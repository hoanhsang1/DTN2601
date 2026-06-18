package java.org.example.backend.controller;

import java.org.example.backend.service.IDepartmentService;
import java.org.example.backend.service.impl.DepartmentServiceImpl;
import java.org.example.entity.Department;

public class DepartmentController {
    IDepartmentService departmentService = new DepartmentServiceImpl();

    public Department findById(int id) {
        return departmentService.findById(id);
    }
}
