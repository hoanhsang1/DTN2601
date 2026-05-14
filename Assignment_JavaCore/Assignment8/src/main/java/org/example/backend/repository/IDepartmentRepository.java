package org.example.backend.repository;

import java.util.List;

import org.example.entity.Department;

public interface IDepartmentRepository {
    List<Department> findAll();
    Department findById(int id);
    List<Department> findByName(String name);
    List<Department> findMostEmployees();
    List<Department> findLeastEmployees();
    boolean create(String name);
    boolean update(int id, String name);
    boolean delete(int id);
}
