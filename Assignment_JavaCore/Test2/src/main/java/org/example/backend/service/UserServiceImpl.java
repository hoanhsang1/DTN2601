package org.example.backend.service;

import org.example.backend.repository.IUserRepository;
import org.example.backend.repository.UserRepositoryImpl;
import org.example.entity.User;

import java.util.List;

public class UserServiceImpl implements IUserService {
    IUserRepository userRepository = new UserRepositoryImpl();

    @Override
    public List<User> findAllUsers() {return userRepository.findAllUsers();}

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {return userRepository.deleteById(id);}

    @Override
    public User login(String email, String password) {
        return userRepository.login(email, password);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userRepository.checkExistEmail(email);
    }

    @Override
    public boolean createEmployee(String fullname, String email) {
        return userRepository.createEmployee(fullname, email);
    }

    @Override
    public boolean registerAdmin(String fullname, String email, String password, int expInYear) {
        return userRepository.registerAdmin(fullname, email, password, expInYear);
    }

    @Override
    public boolean registerEmployee(String fullname, String email, String password, org.example.enums.ProSkill proSkill) {
        return userRepository.registerEmployee(fullname, email, password, proSkill);
    }

    @Override
    public void initializeDatabase() {
        userRepository.initializeDatabase();
    }
}
