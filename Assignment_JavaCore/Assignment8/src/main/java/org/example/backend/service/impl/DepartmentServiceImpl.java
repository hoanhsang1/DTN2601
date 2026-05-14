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

    @Override
    public boolean create(String name) {
        return departmentRepository.create(name);
    }

    @Override
    public boolean update(int id, String name) {
        return departmentRepository.update(id, name);
    }

    @Override
    public boolean delete(int id) {
        return departmentRepository.delete(id);
    }
}
