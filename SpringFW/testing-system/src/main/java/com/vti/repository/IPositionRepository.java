package com.vti.repository;

import com.vti.entity.Position;
import com.vti.enums.PositionName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPositionRepository extends JpaRepository<Position,Integer> {
    Position findByName(PositionName name);

    Position findByNameAndId(PositionName name, Integer id);

    boolean existsByName(PositionName name);

    boolean existsByNameAndIdNot(PositionName name, int id);

    void deleteById(int id);

}
