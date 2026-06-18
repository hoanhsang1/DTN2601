package org.example.frontend;

import org.example.backend.controller.UserController;
import org.example.entity.Admin;
import org.example.entity.Employee;
import org.example.entity.User;
import org.example.enums.ProSkill;
import org.example.utils.ScannerUtils;

import java.util.List;

public class Program {
    private static final UserController userController = new UserController();

    private static void printHeader() {
        System.out.printf("%-5s %-25s %-30s %-12s %-15s %-15s%n",
                "ID", "FULLNAME", "EMAIL", "ROLE", "EXP_IN_YEAR", "PRO_SKILL");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }

    private static void printUser(User u) {
        String role = "USER";
        String exp = "null";
        String skill = "null";

        if (u instanceof Admin) {
            role = "ADMIN";
            exp = String.valueOf(((Admin) u).getExpInYear());
        } else if (u instanceof Employee) {
            role = "EMPLOYEE";
            skill = String.valueOf(((Employee) u).getProSkill());
        }

        System.out.printf("%-5d %-25s %-30s %-12s %-15s %-15s%n",
                u.getId(), u.getFullname(), u.getEmail(), role, exp, skill);
    }

    private static void printSingleUserFC(User u) {
        String detailHeader = "";
        String detailValue = "";
        String role = "USER";

        if (u instanceof Admin) {
            role = "ADMIN";
            detailHeader = "EXP_IN_YEAR";
            detailValue = String.valueOf(((Admin) u).getExpInYear());
        } else if (u instanceof Employee) {
            role = "EMPLOYEE";
            detailHeader = "PRO_SKILL";
            detailValue = String.valueOf(((Employee) u).getProSkill());
        }

        if (!detailHeader.isEmpty()) {
            System.out.printf("%-5s %-25s %-30s %-12s %-15s%n", "ID", "FULLNAME", "EMAIL", "ROLE", detailHeader);
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.printf("%-5d %-25s %-30s %-12s %-15s%n", u.getId(), u.getFullname(), u.getEmail(), role, detailValue);
            System.out.println("-------------------------------------------------------------------------------------------------");
        } else {
            System.out.printf("%-5s %-25s %-30s %-12s%n", "ID", "FULLNAME", "EMAIL", "ROLE");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%-5d %-25s %-30s %-12s%n", u.getId(), u.getFullname(), u.getEmail(), role);
            System.out.println("---------------------------------------------------------------------------------");
        }
    }

    private static void findAllUsersFC() {
        List<User> users = userController.findAllUsers();
        if (users.isEmpty()) {
            System.out.println("Không có người dùng nào trong cơ sở dữ liệu.");
            return;
        }
        printHeader();
        for (User u : users) {
            printUser(u);
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }

    private static void findByIdFC() {
        System.out.println("Nhập ID cần tìm kiếm:");
        int input = ScannerUtils.inputID();
        User a = userController.findById(input);
        if (a == null) {
            System.out.println("Không tìm thấy User với ID = " + input);
            return;
        }
        printSingleUserFC(a);
    }

    private static void deleteByIdFC() {
        System.out.println("Nhập ID của User cần xóa:");
        int id = ScannerUtils.inputID();
        User u = userController.findById(id);
        if (u == null) {
            System.out.println("Không tìm thấy User với ID = " + id);
            return;
        }
        System.out.println("Bạn có chắc chắn muốn xóa User sau đây không?");
        printSingleUserFC(u);
        System.out.println("1. Có, thực hiện xóa");
        System.out.println("2. Không, hủy bỏ");
        int choice = ScannerUtils.inputIntRange(1, 2);
        if (choice == 1) {
            boolean success = userController.deleteById(id);
            if (success) {
                System.out.println("Xóa User thành công!");
            } else {
                System.out.println("Xóa User thất bại!");
            }
        } else {
            System.out.println("Hủy bỏ thao tác xóa.");
        }
    }

    private static void loginFC() {
        System.out.println("--- ĐĂNG NHẬP ---");
        System.out.print("Nhập Email: ");
        String email = ScannerUtils.inputEmail();
        System.out.print("Nhập Password: ");
        String password = ScannerUtils.inputPassword();

        User loggedIn = userController.login(email, password);
        if (loggedIn == null) {
            System.out.println("Đăng nhập thất bại! Sai Email hoặc Password.");
            return;
        }

        System.out.println("\nĐăng nhập thành công!");
        System.out.println("Thông tin tài khoản đăng nhập:");
        printSingleUserFC(loggedIn);

        if (loggedIn instanceof Admin) {
            adminMenu((Admin) loggedIn);
        } else {
            System.out.println("Nhấn Enter để quay lại menu chính...");
            new java.util.Scanner(System.in).nextLine();
        }
    }

    private static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== MENU ADMIN (Xin chào: " + admin.getFullname() + ") ===");
            System.out.println("1. Tạo thêm tài khoản Employee (Question 6)");
            System.out.println("2. Đăng xuất");
            System.out.print("Chọn chức năng (1-2): ");
            int choice = ScannerUtils.inputIntRange(1, 2);
            if (choice == 1) {
                System.out.println("--- TẠO TÀI KHOẢN EMPLOYEE ---");
                System.out.print("Nhập Họ và Tên (FullName) nhân viên mới: ");
                String fullName = ScannerUtils.inputFullName();
                
                String email = "";
                while (true) {
                    System.out.print("Nhập Email nhân viên mới: ");
                    email = ScannerUtils.inputEmail();
                    if (userController.checkExistEmail(email)) {
                        System.out.println("Email này đã tồn tại trong hệ thống! Vui lòng dùng email khác.");
                    } else {
                        break;
                    }
                }

                boolean success = userController.createEmployee(fullName, email);
                if (success) {
                    System.out.println("Tạo Employee thành công! Tài khoản được gán password mặc định là 123456.");
                } else {
                    System.out.println("Tạo Employee thất bại!");
                }
            } else {
                System.out.println("Đã đăng xuất khỏi tài khoản Admin.");
                break;
            }
        }
    }

    private static void registerFC() {
        System.out.println("--- ĐĂNG KÝ TÀI KHOẢN ---");
        System.out.println("Chọn vai trò muốn đăng ký:");
        System.out.println("1. Admin");
        System.out.println("2. Employee");
        int roleChoice = ScannerUtils.inputIntRange(1, 2);

        System.out.print("Nhập Họ và Tên (FullName): ");
        String fullName = ScannerUtils.inputFullName();

        String email = "";
        while (true) {
            System.out.print("Nhập Email: ");
            email = ScannerUtils.inputEmail();
            if (userController.checkExistEmail(email)) {
                System.out.println("Email này đã tồn tại! Vui lòng dùng email khác.");
            } else {
                break;
            }
        }

        System.out.print("Nhập Mật khẩu (từ 6 đến 12 ký tự, có ít nhất 1 chữ hoa): ");
        String password = ScannerUtils.inputPassword();

        boolean success = false;
        if (roleChoice == 1) {
            int expInYear = ScannerUtils.inputExpInYear();
            success = userController.registerAdmin(fullName, email, password, expInYear);
        } else {
            ProSkill proSkill = ScannerUtils.inputProSkill();
            success = userController.registerEmployee(fullName, email, password, proSkill);
        }

        if (success) {
            System.out.println("Đăng ký tài khoản thành công!");
        } else {
            System.out.println("Đăng ký tài khoản thất bại!");
        }
    }

    public static void main(String[] args) {
        // Tự động khởi tạo DB khi bắt đầu chương trình nếu chưa có dữ liệu
        userController.initializeDatabase();

        while (true) {
            System.out.println("\n================= MENU CHƯƠNG TRÌNH QUẢN LÝ =================");
            System.out.println("1. Lấy thông tin tất cả Users (Dạng table)");
            System.out.println("2. Tìm kiếm User theo ID");
            System.out.println("3. Xoá User theo ID");
            System.out.println("4. Đăng nhập (Login)");
            System.out.println("5. Đăng ký tài khoản (Register)");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng (1-6): ");
            int choice = ScannerUtils.inputIntRange(1, 6);
            switch (choice) {
                case 1:
                    findAllUsersFC();
                    break;
                case 2:
                    findByIdFC();
                    break;
                case 3:
                    deleteByIdFC();
                    break;
                case 4:
                    loginFC();
                    break;
                case 5:
                    registerFC();
                    break;
                case 6:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    System.exit(0);
            }
        }
    }
}
