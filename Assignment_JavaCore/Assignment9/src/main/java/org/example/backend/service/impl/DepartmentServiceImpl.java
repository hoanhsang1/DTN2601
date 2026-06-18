package java.org.example.backend.service.impl;

import java.org.example.backend.repository.impl.DepartmentRepositoryImpl;
import java.org.example.backend.service.IDepartmentService;
import java.org.example.entity.Department;

public class DepartmentServiceImpl implements IDepartmentService {

    DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();

    @Override
    public Department findById(int id) {
        return departmentRepository.findById(id);
    }
}
