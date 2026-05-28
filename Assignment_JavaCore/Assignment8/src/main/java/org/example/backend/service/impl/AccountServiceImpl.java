package org.example.backend.service.impl;

import org.example.backend.repository.IAccountRepository;
import org.example.backend.repository.IDepartmentRepository;
import org.example.backend.repository.IPositionRepository;
import org.example.backend.repository.impl.AccountRepositoryImpl;
import org.example.backend.repository.impl.DepartmentRepositoryImpl;
import org.example.backend.repository.impl.PositionRepositoryImpl;
import org.example.backend.service.IAccountService;
import org.example.backend.service.ImportFile;
import org.example.dto.ImportError;
import org.example.entity.Account;
import org.example.entity.Department;
import org.example.entity.Position;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountServiceImpl implements IAccountService, ImportFile<Account> {

    IAccountRepository    accountRepository    = new AccountRepositoryImpl();
    IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
    IPositionRepository   positionRepository   = new PositionRepositoryImpl();

    // ============================================================= FIND
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

    // ============================================================= VALIDATE (dùng chung create/update)
    private String validateAccountFields(Integer id, String username, String fullName, String email,
                                         int departmentId, int positionId) {
        if (id != null) {
            if (id <= 0)                        return "ID phải lớn hơn 0.";
            if (!accountRepository.existsById(id)) return "Account với ID=" + id + " không tồn tại.";
        }

        if (username == null || username.trim().isEmpty()) return "Username không được để trống.";
        String u = username.trim();
        if (id == null) {
            if (accountRepository.existsByUsername(u))
                return "Username \"" + u + "\" đã tồn tại.";
        } else {
            if (accountRepository.existsByUsernameExcludeId(u, id))
                return "Username \"" + u + "\" đã được sử dụng bởi account khác.";
        }

        if (fullName == null || fullName.trim().isEmpty()) return "Họ tên không được để trống.";

        if (email == null || email.trim().isEmpty()) return "Email không được để trống.";
        String e = email.trim();
        if (!e.contains("@") || e.indexOf("@") == 0 || e.indexOf("@") == e.length() - 1)
            return "Email không hợp lệ (phải có ký tự '@' ở giữa).";
        if (id == null) {
            if (accountRepository.existsByEmail(e))
                return "Email \"" + e + "\" đã được sử dụng.";
        } else {
            if (accountRepository.existsByEmailExcludeId(e, id))
                return "Email \"" + e + "\" đã được sử dụng bởi account khác.";
        }

        if (!departmentRepository.existsById(departmentId))
            return "Phòng ban với ID=" + departmentId + " không tồn tại.";
        if (!positionRepository.existsById(positionId))
            return "Chức vụ với ID=" + positionId + " không tồn tại.";

        return null;
    }

    // ============================================================= CREATE
    @Override
    public String create(String username, String fullName, String email,
                         int departmentId, int positionId) {
        String err = validateAccountFields(null, username, fullName, email, departmentId, positionId);
        if (err != null) return err;

        boolean ok = accountRepository.create(username.trim(), fullName.trim(), email.trim(), departmentId, positionId);
        return ok ? null : "Thêm mới thất bại, vui lòng thử lại.";
    }

    // ============================================================= UPDATE
    @Override
    public String update(int id, String username, String fullName, String email,
                         int departmentId, int positionId) {
        String err = validateAccountFields(id, username, fullName, email, departmentId, positionId);
        if (err != null) return err;

        boolean ok = accountRepository.update(id, username.trim(), fullName.trim(), email.trim(), departmentId, positionId);
        return ok ? null : "Cập nhật thất bại, vui lòng thử lại.";
    }

    // ============================================================= DELETE
    @Override
    public String delete(int id) {
        if (id <= 0)                        return "ID phải lớn hơn 0.";
        if (!accountRepository.existsById(id)) return "Account với ID=" + id + " không tồn tại.";
        boolean ok = accountRepository.delete(id);
        return ok ? null : "Xóa thất bại, vui lòng thử lại.";
    }

    // ============================================================= IMPORT CSV (via ImportFile<Account>)
    @Override
    public String importAccountFromCSV(String pathName) {
        return importFile(pathName);
    }

    /**
     * Đọc file CSV, gọi validate() từng dòng, trả về danh sách Account hợp lệ.
     * Dòng lỗi được thu thập vào importErrors.
     */
    @Override
    public List<Account> readFile(String pathName, List<ImportError> importErrors) {
        List<Account> accounts = new ArrayList<>();
        Set<String> seenUsernames = new HashSet<>();
        Set<String> seenEmails    = new HashSet<>();
        boolean firstLine = true;

        java.io.File file = new java.io.File(pathName);
        if (!file.exists() || !file.isFile()) {
            return accounts;   // importFile() sẽ trả lỗi file không tồn tại
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (firstLine) { firstLine = false; continue; }   // bỏ header

                String[] fields = line.split(",");
                List<String> errors = new ArrayList<>();

                // truyền thêm seenUsernames/Emails để kiểm tra trùng lặp trong file
                Account acc = validateRow(fields, errors, seenUsernames, seenEmails);
                if (errors.isEmpty() && acc != null) {
                    accounts.add(acc);
                } else {
                    importErrors.add(new ImportError(line, String.join(" | ", errors)));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return accounts;
    }

    /**
     * Implement validate() của ImportFile<T> — phiên bản không có context trùng lặp.
     * (Dùng khi gọi validate đơn lẻ, không cần Set theo dõi trùng)
     */
    @Override
    public Account validate(String[] fields, List<String> errors) {
        return validateRow(fields, errors, new HashSet<>(), new HashSet<>());
    }

    /**
     * Validate một dòng CSV -> trả về Account nếu hợp lệ, null nếu có lỗi.
     * seenUsernames / seenEmails theo dõi trùng lặp trong phạm vi cùng file import.
     */
    private Account validateRow(String[] fields, List<String> errors,
                                Set<String> seenUsernames, Set<String> seenEmails) {
        if (fields.length < 5) {
            errors.add("Dòng không đủ số cột (yêu cầu 5 cột: username,fullname,email,department_id,position_id)");
            return null;
        }

        String username  = fields[0].trim();
        String fullName  = fields[1].trim();
        String email     = fields[2].trim();
        String deptIdStr = fields[3].trim();
        String posIdStr  = fields[4].trim();

        // 1. Username
        if (username.isEmpty()) {
            errors.add("Username không được để trống");
        } else {
            if (accountRepository.existsByUsername(username))
                errors.add("Username \"" + username + "\" đã tồn tại");
            if (seenUsernames.contains(username.toLowerCase()))
                errors.add("Username bị trùng lặp trong file");
            else
                seenUsernames.add(username.toLowerCase());
        }

        // 2. Họ tên
        if (fullName.isEmpty()) errors.add("Họ tên không được để trống");

        // 3. Email
        if (email.isEmpty()) {
            errors.add("Email không được để trống");
        } else if (!email.contains("@") || email.indexOf("@") == 0 || email.indexOf("@") == email.length() - 1) {
            errors.add("Email không hợp lệ (phải có '@' ở giữa)");
        } else {
            if (accountRepository.existsByEmail(email))
                errors.add("Email \"" + email + "\" đã được sử dụng");
            if (seenEmails.contains(email.toLowerCase()))
                errors.add("Email bị trùng lặp trong file");
            else
                seenEmails.add(email.toLowerCase());
        }

        // 4. Department ID
        int departmentId = -1;
        if (deptIdStr.isEmpty()) {
            errors.add("ID phòng ban không được để trống");
        } else {
            try {
                departmentId = Integer.parseInt(deptIdStr);
                if (!departmentRepository.existsById(departmentId))
                    errors.add("ID phòng ban " + departmentId + " không tồn tại");
            } catch (NumberFormatException ex) {
                errors.add("ID phòng ban phải là số nguyên");
            }
        }

        // 5. Position ID
        int positionId = -1;
        if (posIdStr.isEmpty()) {
            errors.add("ID chức vụ không được để trống");
        } else {
            try {
                positionId = Integer.parseInt(posIdStr);
                if (!positionRepository.existsById(positionId))
                    errors.add("ID chức vụ " + positionId + " không tồn tại");
            } catch (NumberFormatException ex) {
                errors.add("ID chức vụ phải là số nguyên");
            }
        }

        if (!errors.isEmpty()) return null;

        Department dep = new Department(); dep.setId(departmentId);
        Position   pos = new Position();   pos.setId(positionId);

        Account acc = new Account();
        acc.setUsername(username);
        acc.setFullName(fullName);
        acc.setEmail(email);
        acc.setDepartment(dep);
        acc.setPosition(pos);
        return acc;
    }

    @Override
    public boolean insertEntitiesToDB(List<Account> accounts) throws Exception {
        return accountRepository.createAccounts(accounts);
    }

    @Override
    public void exportFileError(String pathError, List<ImportError> importErrors) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(pathError), "UTF-8"))) {
            bw.write("raw_line,message_error");
            bw.newLine();
            for (ImportError err : importErrors) {
                bw.write(err.getLine() + "," + err.getMessage());
                bw.newLine();
            }
        }
    }
}
