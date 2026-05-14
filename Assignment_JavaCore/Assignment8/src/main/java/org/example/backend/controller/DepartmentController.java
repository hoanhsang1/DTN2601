package org.example.backend.controller;

import java.util.List;

import org.example.backend.service.IDepartmentService;
import org.example.backend.service.impl.DepartmentServiceImpl;
import org.example.entity.Department;

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

    public boolean create(String name) {
        return departmentService.create(name);
    }

    public boolean update(int id, String name) {
        return departmentService.update(id, name);
    }

    public boolean delete(int id) {
        return departmentService.delete(id);
    }
}
