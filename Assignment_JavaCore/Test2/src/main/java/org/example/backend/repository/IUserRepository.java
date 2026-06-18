package org.example.backend.repository;

import org.example.entity.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAllUsers();
    User findById(int id);
    boolean deleteById(int id);
    User login(String email, String password);
    boolean checkExistEmail(String email);
    boolean createEmployee(String fullname, String email);
    boolean registerAdmin(String fullname, String email, String password, int expInYear);
    boolean registerEmployee(String fullname, String email, String password, org.example.enums.ProSkill proSkill);
    void initializeDatabase();
}
