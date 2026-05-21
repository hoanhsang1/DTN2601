package org.example.backend.controller;

import org.example.backend.service.IDepartmentService;
import org.example.backend.service.impl.DepartmentServiceImpl;
import org.example.entity.Department;

import java.util.List;

public class DepartmentController {

    IDepartmentService departmentService = new DepartmentServiceImpl();

    public List<Department> findAll() {
        return departmentService.findAll();
    }

    public Department findById(int id) {
        return departmentService.findById(id);
    }

    public List<Department> findByName(String name) {
        return departmentService.findByName(name);
    }

    public List<Department> findMostEmployees() {
        return departmentService.findMostEmployees();
    }

    public List<Department> findLeastEmployees() {
        return departmentService.findLeastEmployees();
    }

    /** Trả về null nếu thành công, thông báo lỗi nếu thất bại. */
    public String create(String name) {
        return departmentService.create(name);
    }

    /** Trả về null nếu thành công, thông báo lỗi nếu thất bại. */
    public String update(int id, String name) {
        return departmentService.update(id, name);
    }

    /** Trả về null nếu thành công, thông báo lỗi nếu thất bại. */
    public String delete(int id) {
        return departmentService.delete(id);
    }

    public String importDepartmentFromCSV(String fileName) {
        return departmentService.importDepartmentFromCSV(fileName);
    }
}
