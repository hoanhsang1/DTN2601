package org.example.backend.service.impl;

import java.util.List;

import org.example.backend.repository.IAccountRepository;
import org.example.backend.repository.IDepartmentRepository;
import org.example.backend.repository.IPositionRepository;
import org.example.backend.repository.impl.AccountRepositoryImpl;
import org.example.backend.repository.impl.DepartmentRepositoryImpl;
import org.example.backend.repository.impl.PositionRepositoryImpl;
import org.example.backend.service.IAccountService;
import org.example.entity.Account;

public class AccountServiceImpl implements IAccountService {

    IAccountRepository  accountRepository    = new AccountRepositoryImpl();
    IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
    IPositionRepository positionRepository   = new PositionRepositoryImpl();

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

    // ------------------------------------------------------------------ CREATE
    @Override
    public String create(String username, String fullName, String email,
                         int departmentId, int positionId) {
        // username: không null, không rỗng, unique
        if (username == null || username.trim().isEmpty()) {
            return "Username không được để trống.";
        }
        username = username.trim();
        if (accountRepository.existsByUsername(username)) {
            return "Username \"" + username + "\" đã tồn tại.";
        }

        // fullName: không null, không rỗng
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Họ tên không được để trống.";
        }
        fullName = fullName.trim();

        // email: không null, không rỗng, có @, unique
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống.";
        }
        email = email.trim();
        if (!email.contains("@") || email.indexOf("@") == 0 || email.indexOf("@") == email.length() - 1) {
            return "Email không hợp lệ (phải có ký tự '@' ở giữa).";
        }
        if (accountRepository.existsByEmail(email)) {
            return "Email \"" + email + "\" đã được sử dụng.";
        }

        // departmentId: phải tồn tại
        if (!departmentRepository.existsById(departmentId)) {
            return "Phòng ban với ID=" + departmentId + " không tồn tại.";
        }

        // positionId: phải tồn tại
        if (!positionRepository.existsById(positionId)) {
            return "Chức vụ với ID=" + positionId + " không tồn tại.";
        }

        boolean ok = accountRepository.create(username, fullName, email, departmentId, positionId);
        return ok ? null : "Thêm mới thất bại, vui lòng thử lại.";
    }

    // ------------------------------------------------------------------ UPDATE
    @Override
    public String update(int id, String username, String fullName, String email,
                         int departmentId, int positionId) {
        // id > 0 và tồn tại
        if (id <= 0) {
            return "ID phải lớn hơn 0.";
        }
        if (!accountRepository.existsById(id)) {
            return "Account với ID=" + id + " không tồn tại.";
        }

        // username: không null, không rỗng, unique (trừ chính nó)
        if (username == null || username.trim().isEmpty()) {
            return "Username không được để trống.";
        }
        username = username.trim();
        if (accountRepository.existsByUsernameExcludeId(username, id)) {
            return "Username \"" + username + "\" đã được sử dụng bởi account khác.";
        }

        // fullName: không null, không rỗng
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Họ tên không được để trống.";
        }
        fullName = fullName.trim();

        // email: không null, không rỗng, có @, unique (trừ chính nó)
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống.";
        }
        email = email.trim();
        if (!email.contains("@") || email.indexOf("@") == 0 || email.indexOf("@") == email.length() - 1) {
            return "Email không hợp lệ (phải có ký tự '@' ở giữa).";
        }
        if (accountRepository.existsByEmailExcludeId(email, id)) {
            return "Email \"" + email + "\" đã được sử dụng bởi account khác.";
        }

        // departmentId: phải tồn tại
        if (!departmentRepository.existsById(departmentId)) {
            return "Phòng ban với ID=" + departmentId + " không tồn tại.";
        }

        // positionId: phải tồn tại
        if (!positionRepository.existsById(positionId)) {
            return "Chức vụ với ID=" + positionId + " không tồn tại.";
        }

        boolean ok = accountRepository.update(id, username, fullName, email, departmentId, positionId);
        return ok ? null : "Cập nhật thất bại, vui lòng thử lại.";
    }

    // ------------------------------------------------------------------ DELETE
    @Override
    public String delete(int id) {
        if (id <= 0) {
            return "ID phải lớn hơn 0.";
        }
        if (!accountRepository.existsById(id)) {
            return "Account với ID=" + id + " không tồn tại.";
        }
        boolean ok = accountRepository.delete(id);
        return ok ? null : "Xóa thất bại, vui lòng thử lại.";
    }
}
