package org.example.backend.controller;

import java.util.List;

import org.example.backend.service.IPositionService;
import org.example.backend.service.impl.PositionServiceImpl;
import org.example.entity.Position;

public class PositionController {

    IPositionService positionService = new PositionServiceImpl();

    public List<Position> findAll() {
        return positionService.findAll();
    }

    public Position findById(int id) {
        return positionService.findById(id);
    }

    public List<Position> findByName(String name) {
        return positionService.findByName(name);
    }

    public List<Position> findMostEmployees() {
        return positionService.findMostEmployees();
    }

    public List<Position> findLeastEmployees() {
        return positionService.findLeastEmployees();
    }

    /** Trả về null nếu thành công, thông báo lỗi nếu thất bại. */
    public String create(String name) {
        return positionService.create(name);
    }

    /** Trả về null nếu thành công, thông báo lỗi nếu thất bại. */
    public String update(int id, String name) {
        return positionService.update(id, name);
    }

    /** Trả về null nếu thành công, thông báo lỗi nếu thất bại. */
    public String delete(int id) {
        return positionService.delete(id);
    }
}
