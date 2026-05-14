package org.example.backend.service.impl;

import java.util.List;

import org.example.backend.repository.IPositionRepository;
import org.example.backend.repository.impl.PositionRepositoryImpl;
import org.example.backend.service.IPositionService;
import org.example.entity.Position;

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

    @Override
    public boolean create(String name) {
        return positionRepository.create(name);
    }

    @Override
    public boolean update(int id, String name) {
        return positionRepository.update(id, name);
    }

    @Override
    public boolean delete(int id) {
        return positionRepository.delete(id);
    }
}
