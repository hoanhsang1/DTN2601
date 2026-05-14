package org.example.backend.service;

import java.util.List;

import org.example.entity.Position;

public interface IPositionService {
    List<Position> findAll();
    Position findById(int id);
    List<Position> findByName(String name);
    List<Position> findMostEmployees();
    List<Position> findLeastEmployees();
    boolean create(String name);
    boolean update(int id, String name);
    boolean delete(int id);
}
