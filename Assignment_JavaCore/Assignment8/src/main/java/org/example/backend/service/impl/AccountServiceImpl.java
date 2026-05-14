package org.example.backend.service.impl;

import java.util.List;

import org.example.backend.repository.IAccountRepository;
import org.example.backend.repository.impl.AccountRepositoryImpl;
import org.example.backend.service.IAccountService;
import org.example.entity.Account;

public class AccountServiceImpl implements IAccountService {

    IAccountRepository accountRepository = new AccountRepositoryImpl();

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findByName(String name) {
        return accountRepository.findByName(name);
    }

    @Override
    public boolean create(String username, String fullName, String email, int departmentId, int positionId) {
        return accountRepository.create(username, fullName, email, departmentId, positionId);
    }

    @Override
    public boolean update(int id, String username, String fullName, String email, int departmentId, int positionId) {
        return accountRepository.update(id, username, fullName, email, departmentId, positionId);
    }

    @Override
    public boolean delete(int id) {
        return accountRepository.delete(id);
    }
}
