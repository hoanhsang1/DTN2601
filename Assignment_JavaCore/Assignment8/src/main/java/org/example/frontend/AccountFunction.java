package org.example.frontend;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.example.backend.controller.AccountController;
import org.example.backend.controller.DepartmentController;
import org.example.backend.controller.PositionController;
import org.example.entity.Account;
import org.example.entity.Department;
import org.example.entity.Position;

public class AccountFunction {

    AccountController    accountController    = new AccountController();
    DepartmentController departmentController = new DepartmentController();
    PositionController   positionController   = new PositionController();
    private final Scanner sc = new Scanner(System.in);

    private static final String BORDER =
            "+-------+--------------------+----------------------+------------------------------+------------------------+----------------+";
    private static final String HEADER_FMT =
            "| %-5s | %-18s | %-20s | %-28s | %-22s | %-14s |%n";
    private static final String ROW_FMT =
            "| %-5s | %-18s | %-20s | %-28s | %-22s | %-14s |%n";

    public void run() {
        while (true) {
            System.out.println("\n========== QUẢN LÝ TÀI KHOẢN ==========");
            System.out.println("1. Xem danh sách account");
            System.out.println("2. Tìm account theo ID");
            System.out.println("3. Tìm account theo họ tên");
            System.out.println("4. Thêm mới account");
            System.out.println("5. Cập nhật account");
            System.out.println("6. Xóa account");
            System.out.println("7. Import từ file csv account");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": showAccount(accountController.findAll()); break;
                case "2": findById();                               break;
                case "3": findByName();                             break;
                case "4": insertAccount();                          break;
                case "5": updateAccount();                          break;
                case "6": deleteAccount();                          break;
                case "7": importAccountFromCSV();                  break;
                case "0": return;
                default:  System.out.println("Lựa chọn không hợp lệ, thử lại.");
            }
        }
    }

    public void showAccount(List<Account> accounts) {
        System.out.println(BORDER);
        System.out.printf(HEADER_FMT, "ID", "Username", "Họ tên", "Email", "Phòng ban", "Chức vụ");
        System.out.println(BORDER);
        if (accounts.isEmpty()) {
            System.out.printf(ROW_FMT, "", "Không tìm thấy", "", "", "", "");
        } else {
            for (Account a : accounts) {
                System.out.printf(ROW_FMT,
                        a.getId(),
                        a.getUsername(),
                        a.getFullName(),
                        a.getEmail(),
                        a.getDepartment().getName(),
                        a.getPosition().getName());
            }
        }
        System.out.println(BORDER);
    }

    private void findById() {
        System.out.print("Nhập ID account: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            Account acc = accountController.findById(id);
            showAccount(acc != null ? Collections.singletonList(acc) : Collections.emptyList());
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
        }
    }

    private void findByName() {
        System.out.print("Nhập họ tên cần tìm: ");
        String name = sc.nextLine().trim();
        showAccount(accountController.findByName(name));
    }

    private void insertAccount() {
        System.out.print("Nhập username: ");
        String username = sc.nextLine().trim();

        System.out.print("Nhập họ tên: ");
        String fullName = sc.nextLine().trim();

        System.out.print("Nhập email: ");
        String email = sc.nextLine().trim();

        int departmentId = chooseDepartment();
        if (departmentId == -1) return;

        int positionId = choosePosition();
        if (positionId == -1) return;

        String error = accountController.create(username, fullName, email, departmentId, positionId);
        if (error == null) {
            System.out.println("[OK] Thêm mới thành công.");
        } else {
            System.out.println("[Lỗi] " + error);
        }
    }

    private void updateAccount() {
        System.out.print("Nhập ID account cần cập nhật: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Nhập username mới: ");
            String username = sc.nextLine().trim();

            System.out.print("Nhập họ tên mới: ");
            String fullName = sc.nextLine().trim();

            System.out.print("Nhập email mới: ");
            String email = sc.nextLine().trim();

            int departmentId = chooseDepartment();
            if (departmentId == -1) return;

            int positionId = choosePosition();
            if (positionId == -1) return;

            String error = accountController.update(id, username, fullName, email, departmentId, positionId);
            if (error == null) {
                System.out.println("[OK] Cập nhật thành công.");
            } else {
                System.out.println("[Lỗi] " + error);
            }
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
        }
    }

    private void deleteAccount() {
        System.out.print("Nhập ID account cần xóa: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            String error = accountController.delete(id);
            if (error == null) {
                System.out.println("[OK] Xóa thành công.");
            } else {
                System.out.println("[Lỗi] " + error);
            }
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
        }
    }

    private void importAccountFromCSV() {
        System.out.println("========== IMPORT ACCOUNT ==========");
        System.out.print("Nhập địa chỉ lưu file Import: ");
        String fileName = sc.nextLine().trim();
        String result = accountController.importAccountFromCSV(fileName);
        System.out.println(result);
    }

    private int chooseDepartment() {
        List<Department> departments = departmentController.findAll();
        System.out.println("+-------+------------------------+");
        System.out.printf("| %-5s | %-22s |%n", "ID", "Tên phòng ban");
        System.out.println("+-------+------------------------+");
        for (Department d : departments) {
            System.out.printf("| %-5s | %-22s |%n", d.getId(), d.getName());
        }
        System.out.println("+-------+------------------------+");
        System.out.print("Nhập ID phòng ban: ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
            return -1;
        }
    }

    private int choosePosition() {
        List<Position> positions = positionController.findAll();
        System.out.println("+-------+----------------+");
        System.out.printf("| %-5s | %-14s |%n", "ID", "Tên chức vụ");
        System.out.println("+-------+----------------+");
        for (Position p : positions) {
            System.out.printf("| %-5s | %-14s |%n", p.getId(), p.getName());
        }
        System.out.println("+-------+----------------+");
        System.out.print("Nhập ID chức vụ: ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
            return -1;
        }
    }
}
