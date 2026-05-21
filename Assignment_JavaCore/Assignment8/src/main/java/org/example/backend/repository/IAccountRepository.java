package org.example.backend.repository;

import java.util.List;

import org.example.entity.Account;

public interface IAccountRepository {
    List<Account> findAll();
    Account findById(int id);
    List<Account> findByName(String name);

    // validation queries
    boolean existsById(int id);
    boolean existsByUsername(String username);
    boolean existsByUsernameExcludeId(String username, int excludeId);
    boolean existsByEmail(String email);
    boolean existsByEmailExcludeId(String email, int excludeId);

    boolean create(String username, String fullName, String email, int departmentId, int positionId);
    boolean update(int id, String username, String fullName, String email, int departmentId, int positionId);
    boolean delete(int id);
    boolean createAccounts(List<Account> accounts) throws java.sql.SQLException;
}
