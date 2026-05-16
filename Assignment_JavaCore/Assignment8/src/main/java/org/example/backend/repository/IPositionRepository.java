package org.example.backend.repository;

import java.util.List;

import org.example.entity.Position;

public interface IPositionRepository {
    List<Position> findAll();
    Position findById(int id);
    List<Position> findByName(String name);
    List<Position> findMostEmployees();
    List<Position> findLeastEmployees();

    // validation queries
    boolean existsById(int id);
    boolean existsByName(String name);

    boolean create(String name);
    boolean update(int id, String name);
    boolean delete(int id);
}
