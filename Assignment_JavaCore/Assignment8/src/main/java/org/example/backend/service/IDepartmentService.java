package org.example.backend.service;

import java.util.List;

import org.example.entity.Department;

public interface IDepartmentService {
    List<Department> findAll();
    Department findById(int id);
    List<Department> findByName(String name);
    List<Department> findMostEmployees();
    List<Department> findLeastEmployees();
    boolean create(String name);
    boolean update(int id, String name);
    boolean delete(int id);
}
