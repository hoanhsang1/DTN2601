package com.vti.service;

import com.vti.entity.Position;

import java.util.List;

public interface IPositionService {
    List<Position> findAll();

    Position findById(Integer id);

    Position findByName(String name);

    Position findByNameAndId(String name, Integer id);

    Position create(Position position);
    Position update(Position position);

    void deleteById(Integer id);
}
