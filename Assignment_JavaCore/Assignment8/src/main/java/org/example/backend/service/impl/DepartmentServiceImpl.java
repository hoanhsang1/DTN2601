package org.example.backend.service.impl;

import java.util.List;

import org.example.backend.repository.IDepartmentRepository;
import org.example.backend.repository.impl.DepartmentRepositoryImpl;
import org.example.backend.service.IDepartmentService;
import org.example.entity.Department;

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
}
