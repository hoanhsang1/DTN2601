package com.vti.service.impl;

import com.vti.entity.Position;
import com.vti.enums.PositionName;
import com.vti.repository.IPositionRepository;
import com.vti.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PositionServiceImpl implements IPositionService {

    @Autowired
    private IPositionRepository positionRepository;

    @Override
    public List<Position> findAll() {

        return positionRepository.findAll();
    }

    @Override
    public Position findById(Integer id) {
        Optional<Position> positions = positionRepository.findById(id);
//        if(positions.isPresent()){
//            return positions.get();
//        } else {
//            return null;
//        }
        return positions.orElse(null);
    }

    @Override
    public Position findByName(String name) {
        PositionName positionName = PositionName.toEnum(name);
        if (positionName == null) {
            try {
                positionName = PositionName.valueOf(name);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        Position position = positionRepository.findByName(positionName);
        return position;
    }

    @Override
    public Position findByNameAndId(String name, Integer id) {
        PositionName positionName = PositionName.toEnum(name);
        if (positionName == null) {
            try {
                positionName = PositionName.valueOf(name);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        Position position = positionRepository.findByNameAndId(positionName,id);
        return position;
    }

    @Override
    public Position create(Position position) {
        positionRepository.save(position);
        return position;
    }

    @Override
    public Position update(Position position) {
        Position existingPosition = positionRepository.findById(position.getId()).orElse(null);
        if (Objects.isNull(existingPosition)) {
            throw new RuntimeException("Id k tồn tại");
        }
        if (positionRepository.existsByNameAndIdNot(position.getName(), position.getId())) {
            throw new RuntimeException("tên đã tồn tại");

        }
        existingPosition.setName(position.getName());
        positionRepository.save(existingPosition);
        return existingPosition;

    }

    @Override
    public void deleteById(Integer id) {
        Position position =positionRepository.findById(id).orElse(null);
        if (Objects.isNull(position)) {
            throw new RuntimeException("Id k tồn tại");
        }
        positionRepository.deleteById(id);
    }
}
