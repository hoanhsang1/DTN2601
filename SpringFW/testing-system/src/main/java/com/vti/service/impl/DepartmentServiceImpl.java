package com.vti.service.impl;

import com.vti.entity.Department;
import com.vti.repository.IDepartmentRepository;
import com.vti.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private IDepartmentRepository departmentRespository;

    @Override
    public List<Department> findAll() {

        return departmentRespository.findAll();
    }

    @Override
    public Department findById(Integer id) {
        Optional<Department> departments = departmentRespository.findById(id);
//        if(departments.isPresent()){
//            return departments.get();
//        } else {
//            return null;
//        }
        return departments.orElse(null);
    }

    @Override
    public Department findByName(String name) {
        Department department = departmentRespository.findByName(name);
        return department;
    }

    @Override
    public Department findByNameAndId(String name, Integer id) {
        Department department = departmentRespository.findByNameAndId(name,id);
        return department;
    }

    @Override
    public Department create(Department department) {
        departmentRespository.save(department);
        return department;
    }

    @Override
    public Department update(Department department) {
        Department department1 =departmentRespository.findById(department.getId()).orElse(null);
        if (Objects.isNull(department1)) {
            throw new RuntimeException("Id k tồn tại");
        }
        if (departmentRespository.existsByNameAndIdNot(department.getName(), department.getId())) {
            throw new RuntimeException("tên đã tồn tại");

        }
        department1.setName(department.getName());
        departmentRespository.save(department1);
        return department1;

    }

    @Override
    public void deleteById(Integer id) {
        Department department =departmentRespository.findById(id).orElse(null);
        if (Objects.isNull(department)) {
            throw new RuntimeException("Id k tồn tại");
        }
        departmentRespository.deleteById(id);
    }
}
