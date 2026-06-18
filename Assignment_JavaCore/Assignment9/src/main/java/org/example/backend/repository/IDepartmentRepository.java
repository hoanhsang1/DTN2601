package java.org.example.backend.repository;

import java.org.example.entity.Department;

public interface IDepartmentRepository {
    Department findById(int id);

}
