package org.example.backend.controller;

import java.util.List;

import org.example.backend.service.IAccountService;
import org.example.backend.service.impl.AccountServiceImpl;
import org.example.entity.Account;

public class AccountController {

    IAccountService accountService = new AccountServiceImpl();

    public List<Account> findAll() {
        return accountService.findAll();
    }

    public Account findById(int id) {
        return accountService.findById(id);
    }

    public List<Account> findByName(String name) {
        return accountService.findByName(name);
    }

    public boolean create(String username, String fullName, String email, int departmentId, int positionId) {
        return accountService.create(username, fullName, email, departmentId, positionId);
    }

    public boolean update(int id, String username, String fullName, String email, int departmentId, int positionId) {
        return accountService.update(id, username, fullName, email, departmentId, positionId);
    }

    public boolean delete(int id) {
        return accountService.delete(id);
    }
}
