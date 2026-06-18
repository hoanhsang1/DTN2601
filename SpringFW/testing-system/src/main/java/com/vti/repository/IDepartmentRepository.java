package com.vti.repository;

import com.vti.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<Department,Integer> {
    Department findByName(String name);

    Department findByNameAndId(String name, Integer id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, int id);

    void deleteById(int id);

}
