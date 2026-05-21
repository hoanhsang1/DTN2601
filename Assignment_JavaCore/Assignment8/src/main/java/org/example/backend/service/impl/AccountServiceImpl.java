package org.example.backend.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public String importAccountFromCSV(String pathName) {
        java.io.File file = new java.io.File(pathName);
        if (!file.exists() || !file.isFile()) {
            return "[Lỗi] File không tồn tại: " + pathName;
        }

        List<Account> accounts = new ArrayList<>();
        List<ImportError> importErrors = new ArrayList<>();
        Set<String> importedUsernames = new HashSet<>();
        Set<String> importedEmails = new HashSet<>();
        boolean firstLine = true;

        try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                List<String> errors = new ArrayList<>();
                // CSV format: username, fullname, email, department_id, position_id
                String[] fields = line.split(",");
                if (fields.length < 5) {
                    errors.add("Dòng không đủ số cột (yêu cầu 5 cột: username,fullname,email,department_id,position_id)");
                    importErrors.add(new ImportError(line, String.join(" | ", errors)));
                    continue;
                }

                String username = fields[0] != null ? fields[0].trim() : "";
                String fullName = fields[1] != null ? fields[1].trim() : "";
                String email = fields[2] != null ? fields[2].trim() : "";
                String deptIdStr = fields[3] != null ? fields[3].trim() : "";
                String posIdStr = fields[4] != null ? fields[4].trim() : "";

                // 1. Validate username
                if (username.isEmpty()) {
                    errors.add("Username không được để trống");
                } else {
                    if (accountRepository.existsByUsername(username)) {
                        errors.add("Username \"" + username + "\" đã tồn tại");
                    }
                    if (importedUsernames.contains(username.toLowerCase())) {
                        errors.add("Username bị trùng lặp trong file");
                    } else {
                        importedUsernames.add(username.toLowerCase());
                    }
                }

                // 2. Validate fullName
                if (fullName.isEmpty()) {
                    errors.add("Họ tên không được để trống");
                }

                // 3. Validate email
                if (email.isEmpty()) {
                    errors.add("Email không được để trống");
                } else {
                    if (!email.contains("@") || email.indexOf("@") == 0 || email.indexOf("@") == email.length() - 1) {
                        errors.add("Email không hợp lệ (phải có '@' ở giữa)");
                    } else {
                        if (accountRepository.existsByEmail(email)) {
                            errors.add("Email \"" + email + "\" đã được sử dụng");
                        }
                        if (importedEmails.contains(email.toLowerCase())) {
                            errors.add("Email bị trùng lặp trong file");
                        } else {
                            importedEmails.add(email.toLowerCase());
                        }
                    }
                }

                // 4. Validate departmentId
                int departmentId = -1;
                if (deptIdStr.isEmpty()) {
                    errors.add("ID phòng ban không được để trống");
                } else {
                    try {
                        departmentId = Integer.parseInt(deptIdStr);
                        if (!departmentRepository.existsById(departmentId)) {
                            errors.add("ID phòng ban " + departmentId + " không tồn tại");
                        }
                    } catch (NumberFormatException e) {
                        errors.add("ID phòng ban phải là số nguyên");
                    }
                }

                // 5. Validate positionId
                int positionId = -1;
                if (posIdStr.isEmpty()) {
                    errors.add("ID chức vụ không được để trống");
                } else {
                    try {
                        positionId = Integer.parseInt(posIdStr);
                        if (!positionRepository.existsById(positionId)) {
                            errors.add("ID chức vụ " + positionId + " không tồn tại");
                        }
                    } catch (NumberFormatException e) {
                        errors.add("ID chức vụ phải là số nguyên");
                    }
                }

                if (errors.isEmpty()) {
                    org.example.entity.Department dep = new org.example.entity.Department();
                    dep.setId(departmentId);
                    org.example.entity.Position pos = new org.example.entity.Position();
                    pos.setId(positionId);

                    Account acc = new Account();
                    acc.setUsername(username);
                    acc.setFullName(fullName);
                    acc.setEmail(email);
                    acc.setDepartment(dep);
                    acc.setPosition(pos);

                    accounts.add(acc);
                } else {
                    importErrors.add(new ImportError(line, String.join(" | ", errors)));
                }
            }
        } catch (Exception e) {
            return "[Lỗi] Không thể đọc file: " + e.getMessage();
        }

        String pathError;
        if (pathName.toLowerCase().endsWith(".csv")) {
            pathError = pathName.substring(0, pathName.length() - 4) + "_error.csv";
        } else {
            pathError = pathName + "_error.csv";
        }

        if (!importErrors.isEmpty()) {
            try (BufferedWriter bw = new BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathError), "UTF-8"))) {
                bw.write("raw_line,message_error");
                bw.newLine();
                for (ImportError error : importErrors) {
                    bw.write(error.getLine() + "," + error.getMessage());
                    bw.newLine();
                }
            } catch (Exception e) {
                return "[Lỗi] Không thể ghi file lỗi: " + e.getMessage();
            }
        }

        boolean checkImport = false;
        if (!accounts.isEmpty()) {
            try {
                checkImport = accountRepository.createAccounts(accounts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            checkImport = true;
        }

        if (importErrors.isEmpty()) {
            return "Import thành công. Đã nhập " + accounts.size() + " tài khoản.";
        } else {
            if (accounts.isEmpty()) {
                return "Import thất bại, toàn bộ " + importErrors.size() + " dòng đều bị lỗi. Chi tiết lỗi đã được xuất ra: " + pathError;
            }
            return checkImport
                ? "Import hoàn tất (có lỗi). Đã nhập " + accounts.size() + " tài khoản. " + importErrors.size() + " dòng bị lỗi (đã xuất file lỗi tại " + pathError + ")."
                : "Lỗi kết nối cơ sở dữ liệu khi import. Chi tiết lỗi đã được xuất ra: " + pathError;
        }
    }
}
