package org.example.backend.service.impl;

import org.example.backend.repository.IDepartmentRepository;
import org.example.backend.repository.impl.DepartmentRepositoryImpl;
import org.example.backend.service.IDepartmentService;
import org.example.entity.Department;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DepartmentServiceImpl implements IDepartmentService {

    IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();

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

    // ------------------------------------------------------------------ CREATE
    @Override
    public String create(String name) {
        // validate: null hoặc rỗng
        if (name == null || name.trim().isEmpty()) {
            return "Tên phòng ban không được để trống.";
        }
        name = name.trim();
        // validate: đã tồn tại chưa (unique)
        if (departmentRepository.existsByName(name)) {
            return "Tên phòng ban \"" + name + "\" đã tồn tại.";
        }
        boolean ok = departmentRepository.create(name);
        return ok ? null : "Thêm mới thất bại, vui lòng thử lại.";
    }

    // ------------------------------------------------------------------ UPDATE
    @Override
    public String update(int id, String name) {
        // validate id > 0
        if (id <= 0) {
            return "ID phải lớn hơn 0.";
        }
        // validate id tồn tại
        if (!departmentRepository.existsById(id)) {
            return "Phòng ban với ID=" + id + " không tồn tại.";
        }
        // validate tên
        if (name == null || name.trim().isEmpty()) {
            return "Tên phòng ban không được để trống.";
        }
        name = name.trim();
        // validate unique: tên mới không được trùng với phòng ban khác
        // (cho phép giữ nguyên tên của chính nó)
        if (departmentRepository.existsByNameExcludeId(name, id)) {
            return "Tên phòng ban \"" + name + "\" đã được sử dụng bởi phòng ban khác.";
        }
        boolean ok = departmentRepository.update(id, name);
        return ok ? null : "Cập nhật thất bại, vui lòng thử lại.";
    }

    // ------------------------------------------------------------------ DELETE
    @Override
    public String delete(int id) {
        // validate id > 0
        if (id <= 0) {
            return "ID phải lớn hơn 0.";
        }
        // validate id tồn tại
        if (!departmentRepository.existsById(id)) {
            return "Phòng ban với ID=" + id + " không tồn tại.";
        }
        boolean ok = departmentRepository.delete(id);
        return ok ? null : "Xóa thất bại, vui lòng thử lại.";
    }

    @Override
    public String importDepartmentFromCSV(String pathName) {
        java.io.File file = new java.io.File(pathName);
        if (!file.exists() || !file.isFile()) {
            return "[Lỗi] File không tồn tại: " + pathName;
        }

        List<Department> departments = new ArrayList<>();
        List<ImportError> importErrors = new ArrayList<>();
        java.util.Set<String> importedNames = new java.util.HashSet<>();
        boolean firstLine = true;

        try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                List<String> errors = new ArrayList<>();
                String[] fields = line.split(",");
                String departmentName = fields.length > 0 ? fields[0] : "";

                if (Objects.isNull(departmentName) || departmentName.trim().isEmpty()) {
                    errors.add("Tên phòng ban không được để trống");
                } else {
                    String deptNameTrimmed = departmentName.trim();
                    if (departmentRepository.existsByName(deptNameTrimmed)) {
                        errors.add("Tên phòng ban đã tồn tại");
                    }
                    if (importedNames.contains(deptNameTrimmed.toLowerCase())) {
                        errors.add("Tên phòng ban bị trùng lặp trong file");
                    } else {
                        importedNames.add(deptNameTrimmed.toLowerCase());
                    }
                }

                if (errors.isEmpty()) {
                    Department dep = new Department(departmentName.trim());
                    departments.add(dep);
                } else {
                    importErrors.add(new ImportError(line, String.join(" | ", errors)));
                }
            }
        } catch (Exception e) {
            return "[Lỗi] Không thể đọc file: " + e.getMessage();
        }

        String pathError;
        if (pathName.toLowerCase().endsWith(".csv")) {
            pathError = pathName.substring(0, pathName.length() - 4) + "_error.csv";
        } else {
            pathError = pathName + "_error.csv";
        }

        if (!importErrors.isEmpty()) {
            try (BufferedWriter bw = new BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathError), "UTF-8"))) {
                bw.write("department_name,message_error");
                bw.newLine();
                for (ImportError error : importErrors) {
                    bw.write(error.getLine() + "," + error.getMessage());
                    bw.newLine();
                }
            } catch (Exception e) {
                return "[Lỗi] Không thể ghi file lỗi: " + e.getMessage();
            }
        }

        boolean checkImport = false;
        if (!departments.isEmpty()) {
            try {
                checkImport = departmentRepository.createDepartments(departments);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            checkImport = true;
        }

        if (importErrors.isEmpty()) {
            return "Import thành công. Đã nhập " + departments.size() + " phòng ban.";
        } else {
            if (departments.isEmpty()) {
                return "Import thất bại, toàn bộ " + importErrors.size() + " dòng đều bị lỗi. Chi tiết lỗi đã được xuất ra: " + pathError;
            }
            return checkImport
                ? "Import hoàn tất (có lỗi). Đã nhập " + departments.size() + " phòng ban. " + importErrors.size() + " dòng bị lỗi (đã xuất file lỗi tại " + pathError + ")."
                : "Lỗi kết nối cơ sở dữ liệu khi import. Chi tiết lỗi đã được xuất ra: " + pathError;
        }
    }
}

class ImportError {
    private final String line;
    private final String message;

    public ImportError(String line, String message) {
        this.line = line;
        this.message = message;
    }

    public String getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }
}
