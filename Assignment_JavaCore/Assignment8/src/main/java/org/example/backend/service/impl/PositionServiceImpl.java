package org.example.backend.service.impl;

import java.util.List;

import org.example.backend.repository.IPositionRepository;
import org.example.backend.repository.impl.PositionRepositoryImpl;
import org.example.backend.service.IPositionService;
import org.example.entity.Position;
import org.example.enums.PositionName;

public class PositionServiceImpl implements IPositionService {

    IPositionRepository positionRepository = new PositionRepositoryImpl();

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @Override
    public Position findById(int id) {
        return positionRepository.findById(id);
    }

    @Override
    public List<Position> findByName(String name) {
        return positionRepository.findByName(name);
    }

    @Override
    public List<Position> findMostEmployees() {
        return positionRepository.findMostEmployees();
    }

    @Override
    public List<Position> findLeastEmployees() {
        return positionRepository.findLeastEmployees();
    }

    // ------------------------------------------------------------------ CREATE
    @Override
    public String create(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Tên chức vụ không được để trống.";
        }
        name = name.trim().toUpperCase();
        // validate: phải là giá trị enum hợp lệ
        try {
            PositionName.valueOf(name);
        } catch (IllegalArgumentException e) {
            return "Tên chức vụ không hợp lệ. Các giá trị cho phép: DEV, TEST, SCRUM_MASTER, PM.";
        }
        // validate: đã tồn tại chưa (unique)
        if (positionRepository.existsByName(name)) {
            return "Chức vụ \"" + name + "\" đã tồn tại.";
        }
        boolean ok = positionRepository.create(name);
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
        if (!positionRepository.existsById(id)) {
            return "Chức vụ với ID=" + id + " không tồn tại.";
        }
        if (name == null || name.trim().isEmpty()) {
            return "Tên chức vụ không được để trống.";
        }
        name = name.trim().toUpperCase();
        // validate enum
        try {
            PositionName.valueOf(name);
        } catch (IllegalArgumentException e) {
            return "Tên chức vụ không hợp lệ. Các giá trị cho phép: DEV, TEST, SCRUM_MASTER, PM.";
        }
        // validate unique (không dùng excludeId vì position_name là ENUM — chỉ 4 giá trị)
        if (positionRepository.existsByName(name)) {
            return "Chức vụ \"" + name + "\" đã tồn tại.";
        }
        boolean ok = positionRepository.update(id, name);
        return ok ? null : "Cập nhật thất bại, vui lòng thử lại.";
    }

    // ------------------------------------------------------------------ DELETE
    @Override
    public String delete(int id) {
        if (id <= 0) {
            return "ID phải lớn hơn 0.";
        }
        if (!positionRepository.existsById(id)) {
            return "Chức vụ với ID=" + id + " không tồn tại.";
        }
        boolean ok = positionRepository.delete(id);
        return ok ? null : "Xóa thất bại, vui lòng thử lại.";
    }
}
