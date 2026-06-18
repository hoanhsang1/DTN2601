package org.example.backend.controller;

import org.example.backend.service.IUserService;
import org.example.backend.service.UserServiceImpl;
import org.example.entity.User;

import java.util.List;

public class UserController {
    IUserService userService = new UserServiceImpl();

    public UserController() {
    }

    public List<User> findAllUsers(){return userService.findAllUsers();}

    public User findById(int id){return userService.findById(id);}

    public boolean deleteById(int id){return userService.deleteById(id);}

    public User login(String email, String password) {
        return userService.login(email, password);
    }

    public boolean checkExistEmail(String email) {
        return userService.checkExistEmail(email);
    }

    public boolean createEmployee(String fullname, String email) {
        return userService.createEmployee(fullname, email);
    }

    public boolean registerAdmin(String fullname, String email, String password, int expInYear) {
        return userService.registerAdmin(fullname, email, password, expInYear);
    }

    public boolean registerEmployee(String fullname, String email, String password, org.example.enums.ProSkill proSkill) {
        return userService.registerEmployee(fullname, email, password, proSkill);
    }

    public void initializeDatabase() {
        userService.initializeDatabase();
    }
}
