package org.example.backend.service;

import org.example.entity.Department;

import java.util.List;

public interface IDepartmentService {
    List<Department> findAll();
    Department findById(int id);
    List<Department> findByName(String name);
    List<Department> findMostEmployees();
    List<Department> findLeastEmployees();

    /**
     * Trả về null nếu thành công, chuỗi thông báo lỗi nếu validation thất bại.
     */
    String create(String name);
    String update(int id, String name);
    String delete(int id);

    String importDepartmentFromCSV(String fileName);
}
