package org.example.backend.service;

import java.util.List;

import org.example.entity.Account;

public interface IAccountService {
    List<Account> findAll();
    Account findById(int id);
    List<Account> findByName(String name);
    boolean create(String username, String fullName, String email, int departmentId, int positionId);
    boolean update(int id, String username, String fullName, String email, int departmentId, int positionId);
    boolean delete(int id);
}
