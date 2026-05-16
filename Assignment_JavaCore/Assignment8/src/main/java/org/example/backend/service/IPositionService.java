package org.example.backend.service;

import java.util.List;

import org.example.entity.Position;

public interface IPositionService {
    List<Position> findAll();
    Position findById(int id);
    List<Position> findByName(String name);
    List<Position> findMostEmployees();
    List<Position> findLeastEmployees();

    /**
     * Trả về null nếu thành công, chuỗi thông báo lỗi nếu validation thất bại.
     */
    String create(String name);
    String update(int id, String name);
    String delete(int id);
}
