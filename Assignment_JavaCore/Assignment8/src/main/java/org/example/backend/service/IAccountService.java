package org.example.backend.service;

import java.util.List;

import org.example.entity.Account;

public interface IAccountService {
    List<Account> findAll();
    Account findById(int id);
    List<Account> findByName(String name);

    /**
     * Trả về null nếu thành công, chuỗi thông báo lỗi nếu validation thất bại.
     */
    String create(String username, String fullName, String email, int departmentId, int positionId);
    String update(int id, String username, String fullName, String email, int departmentId, int positionId);
    String delete(int id);
    String importAccountFromCSV(String fileName);
}
