package org.example.backend.service.impl;

import org.example.backend.repository.IDepartmentRepository;
import org.example.backend.repository.impl.DepartmentRepositoryImpl;
import org.example.backend.service.IDepartmentService;
import org.example.backend.service.ImportFile;
import org.example.dto.ImportError;
import org.example.entity.Department;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepartmentServiceImpl implements IDepartmentService, ImportFile<Department> {

    IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();

    // ============================================================= FIND
    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(int id) {
        return departmentRepository.findById(id);
    }

    @Override
    public List<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public List<Department> findMostEmployees() {
        return departmentRepository.findMostEmployees();
    }

    @Override
    public List<Department> findLeastEmployees() {
        return departmentRepository.findLeastEmployees();
    }

    // ============================================================= VALIDATE (dùng chung create/update)
    private String validateDepartmentFields(Integer id, String name) {
        if (id != null) {
            if (id <= 0)                             return "ID phải lớn hơn 0.";
            if (!departmentRepository.existsById(id)) return "Phòng ban với ID=" + id + " không tồn tại.";
        }

        if (name == null || name.trim().isEmpty()) return "Tên phòng ban không được để trống.";
        String nameTrimmed = name.trim();

        if (id == null) {
            if (departmentRepository.existsByName(nameTrimmed))
                return "Tên phòng ban \"" + nameTrimmed + "\" đã tồn tại.";
        } else {
            if (departmentRepository.existsByNameExcludeId(nameTrimmed, id))
                return "Tên phòng ban \"" + nameTrimmed + "\" đã được sử dụng bởi phòng ban khác.";
        }

        return null;
    }

    // ============================================================= CREATE
    @Override
    public String create(String name) {
        String err = validateDepartmentFields(null, name);
        if (err != null) return err;

        boolean ok = departmentRepository.create(name.trim());
        return ok ? null : "Thêm mới thất bại, vui lòng thử lại.";
    }

    // ============================================================= UPDATE
    @Override
    public String update(int id, String name) {
        String err = validateDepartmentFields(id, name);
        if (err != null) return err;

        boolean ok = departmentRepository.update(id, name.trim());
        return ok ? null : "Cập nhật thất bại, vui lòng thử lại.";
    }

    // ============================================================= DELETE
    @Override
    public String delete(int id) {
        if (id <= 0)                             return "ID phải lớn hơn 0.";
        if (!departmentRepository.existsById(id)) return "Phòng ban với ID=" + id + " không tồn tại.";
        boolean ok = departmentRepository.delete(id);
        return ok ? null : "Xóa thất bại, vui lòng thử lại.";
    }

    // ============================================================= IMPORT CSV (via ImportFile<Department>)
    @Override
    public String importDepartmentFromCSV(String pathName) {
        return importFile(pathName);
    }

    /**
     * Đọc file CSV, gọi validateRow() từng dòng, trả về danh sách Department hợp lệ.
     * Dòng lỗi được thu thập vào importErrors.
     */
    @Override
    public List<Department> readFile(String pathName, List<ImportError> importErrors) {
        List<Department> departments = new ArrayList<>();
        Set<String> seenNames = new HashSet<>();  // theo dõi tên trùng lặp trong file
        boolean firstLine = true;

        java.io.File file = new java.io.File(pathName);
        if (!file.exists() || !file.isFile()) {
            return departments;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (firstLine) { firstLine = false; continue; }  // bỏ header

                String[] fields = line.split(",");
                List<String> errors = new ArrayList<>();

                Department dept = validateRow(fields, errors, seenNames);
                if (errors.isEmpty() && dept != null) {
                    departments.add(dept);
                } else {
                    importErrors.add(new ImportError(line, String.join(" | ", errors)));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return departments;
    }

    /**
     * Implement validate() của ImportFile<T> — phiên bản không có context trùng lặp.
     * (Dùng khi validate đơn lẻ 1 dòng, không trong vòng lặp đọc file)
     */
    @Override
    public Department validate(String[] fields, List<String> errors) {
        return validateRow(fields, errors, new HashSet<>());
    }

    /**
     * Validate một dòng CSV → trả về Department nếu hợp lệ, null nếu có lỗi.
     * seenNames theo dõi tên trùng lặp trong phạm vi cùng file import.
     */
    private Department validateRow(String[] fields, List<String> errors, Set<String> seenNames) {
        String name = fields.length > 0 ? fields[0].trim() : "";

        // 1. Tên không được rỗng
        if (name.isEmpty()) {
            errors.add("Tên phòng ban không được để trống");
        } else {
            // 2. Trùng trong DB
            if (departmentRepository.existsByName(name))
                errors.add("Tên phòng ban \"" + name + "\" đã tồn tại");

            // 3. Trùng trong file
            if (seenNames.contains(name.toLowerCase()))
                errors.add("Tên phòng ban bị trùng lặp trong file");
            else
                seenNames.add(name.toLowerCase());
        }

        if (!errors.isEmpty()) return null;

        return new Department(name);
    }

    @Override
    public boolean insertEntitiesToDB(List<Department> departments) throws Exception {
        return departmentRepository.createDepartments(departments);
    }

    @Override
    public void exportFileError(String pathError, List<ImportError> importErrors) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(pathError), "UTF-8"))) {
            bw.write("department_name,message_error");
            bw.newLine();
            for (ImportError err : importErrors) {
                bw.write(err.getLine() + "," + err.getMessage());
                bw.newLine();
            }
        }
    }
}
