package com.vti.frontend;

import com.vti.backend.QLAccount;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;
import java.util.List;
import java.util.Scanner;

public class AccountFunction {
    private static final Scanner sc = new Scanner(System.in);
    private static final QLAccount qlAccount = new QLAccount();

    public static void run() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ TÀI KHOẢN (ACCOUNT) ===");
            System.out.println("1. Danh sách Account");
            System.out.println("2. Thêm mới Account");
            System.out.println("3. Cập nhật Account theo ID");
            System.out.println("4. Xóa Account theo ID");
            System.out.println("5. Xóa Account theo tên");
            System.out.println("6. Tìm kiếm theo tên");
            System.out.println("7. Quay lại Menu chính");
            System.out.print("Mời bạn chọn: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    List<Account> list = qlAccount.getListAccount();
                    showAccountTable(list);
                    break;
                case "2":
                    insertAccount();
                    break;
                case "3":
                    updateAccount();
                    break;
                case "4":
                    deleteAccountById();
                    break;
                case "5":
                    deleteAccountByName();
                    break;
                case "6":
                    findByName();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
            }
        }
    }

    private static void showAccountTable(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            System.out.println(">>> Không có dữ liệu account.");
            return;
        }
        String line = "+----+----------------------+------------+----------------+---------------+---------------+";
        System.out.println(line);
        System.out.printf("|%-4s|%-22s|%-12s|%-16s|%-15s|%-15s|%n",
                "ID", "Email", "Username", "Fullname", "Department", "Position");
        System.out.println(line);
        for (Account acc : accounts) {
            System.out.printf("|%-4d|%-22s|%-12s|%-16s|%-15s|%-15s|%n",
                    acc.getAccountId(),
                    acc.getEmail(),
                    acc.getUsername(),
                    acc.getFullname(),
                    acc.getDepartment().getDepartmentName(),
                    acc.getPosition().getPositionName());
        }
        System.out.println(line);
    }

    private static void insertAccount() {
        try {
            System.out.print("Nhập Email: ");
            String email = sc.nextLine();
            System.out.print("Nhập Username: ");
            String user = sc.nextLine();
            System.out.print("Nhập Fullname: ");
            String full = sc.nextLine();
            System.out.print("Nhập ID phòng ban: ");
            int deptId = Integer.parseInt(sc.nextLine());
            System.out.print("Nhập ID chức vụ: ");
            int posId = Integer.parseInt(sc.nextLine());

            Account acc = new Account();
            acc.setEmail(email);
            acc.setUsername(user);
            acc.setFullname(full);

            Department d = new Department();
            d.setDepartmentId(deptId);
            acc.setDepartmentId(d);

            Position p = new Position();
            p.setPositionId(posId);
            acc.setPositionId(p);

            if (qlAccount.createAccount(acc)) {
                System.out.println("Thêm mới thành công!");
            } else {
                System.out.println("Thêm mới thất bại.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
        }
    }

    private static void updateAccount() {
        try {
            System.out.print("Nhập ID Account cần sửa: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Nhập Email mới: ");
            String email = sc.nextLine();
            System.out.print("Nhập Username mới: ");
            String user = sc.nextLine();
            System.out.print("Nhập Fullname mới: ");
            String full = sc.nextLine();
            System.out.print("Nhập ID phòng ban mới: ");
            int deptId = Integer.parseInt(sc.nextLine());
            System.out.print("Nhập ID chức vụ mới: ");
            int posId = Integer.parseInt(sc.nextLine());

            Account acc = new Account();
            acc.setAccountId(id);
            acc.setEmail(email);
            acc.setUsername(user);
            acc.setFullname(full);

            Department d = new Department();
            d.setDepartmentId(deptId);
            acc.setDepartmentId(d);

            Position p = new Position();
            p.setPositionId(posId);
            acc.setPositionId(p);

            if (qlAccount.updateAccount(acc)) {
                System.out.println("Cập nhật thành công Account ID: " + id);
            } else {
                System.out.println("Cập nhật thất bại. Kiểm tra lại ID Account hoặc ID phòng ban/chức vụ.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
        }
    }

    private static void deleteAccountById() {
        try {
            System.out.print("Nhập ID Account cần xóa: ");
            int id = Integer.parseInt(sc.nextLine());

            if (qlAccount.deleteAccount(id)) {
                System.out.println("Xóa thành công!");
            } else {
                System.out.println("Xóa thất bại (ID không tồn tại).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập ID là một con số.");
        }
    }

    private static void deleteAccountByName() {
        System.out.print("Nhập Fullname Account cần xóa: ");
        String name = sc.nextLine().trim();

        if (qlAccount.deleteByName(name)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại (tên không tồn tại).");
        }
    }

    private static void findByName() {
        System.out.print("Nhập tên cần tìm (fullname): ");
        String name = sc.nextLine().trim();

        List<Account> list = qlAccount.findByName(name);
        showAccountTable(list);
    }
}
