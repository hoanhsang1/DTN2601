package com.vti.frontend;

import com.vti.backend.QLDepartment;
import com.vti.entity.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepartmentFunction {
    private static Scanner sc = new Scanner(System.in);
    // Khởi tạo đối tượng backend để gọi các hàm xử lý database
    private static QLDepartment qlDepartment = new QLDepartment();

    public static void run() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ PHÒNG BAN (DEPARTMENT) ===");
            System.out.println("1. Xem danh sách phòng ban");
            System.out.println("2. Thêm mới phòng ban");
            System.out.println("3. Xóa phòng ban theo ID");
            System.out.println("4. Cập nhật tên phòng ban theo ID");
            System.out.println("5. Tìm kiếm theo ID và Tên");
            System.out.println("6. Phòng ban đông nhân viên nhất");
            System.out.println("7. Phòng ban ít nhân viên nhất");
            System.out.println("8. Quay lại Menu chính");
            System.out.print("Mời bạn chọn: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    List<Department> departments = qlDepartment.getListDepartment();
                    showDepartment(departments);
                    break;
                case "2":
                    insertDepartment();
                    break;
                case "3":
                    deleteDepartment();
                    break;
                case "4":
                    updateDepartment();
                    break;
                case "5":
                    findByIdAndName();
                    break;
                case "6":
                    qlDepartment.getDepartmentsWithMostAccounts();
                    break;
                case "7":
                    qlDepartment.getDepartmentsWithLeastAccounts();
                    break;
                case "8":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
            }
        }
    }

    public static void showDepartment(List<Department> departments) {
        if (departments == null || departments.isEmpty()) {
            System.out.println(">>> Không có dữ liệu phòng ban.");
            return;
        }
        System.out.println("+------+---------------------------+");
        System.out.printf("| %-4s | %-25s |%n", "ID", "Tên phòng ban");
        System.out.println("+------+---------------------------+");
        for (Department dept : departments) {
            System.out.printf("| %-4d | %-25s |%n", dept.getDepartmentId(), dept.getDepartmentName());
        }
        System.out.println("+------+---------------------------+");
    }

    private static void insertDepartment() {
        System.out.print("Nhập tên phòng ban mới: ");
        String name = sc.nextLine();
        if (qlDepartment.createDepartment(name)) {
            System.out.println("Thêm mới thành công!");
        } else {
            System.out.println("Thêm mới thất bại.");
        }
    }

    private static void deleteDepartment() {
        System.out.print("Nhập ID phòng ban cần xóa: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (qlDepartment.deleteDepartment(id)) {
                System.out.println("Xóa thành công!");
            } else {
                System.out.println("Xóa thất bại (ID không tồn tại hoặc có ràng buộc nhân viên).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập ID là một con số.");
        }
    }

    private static void updateDepartment() {
        try {
            System.out.print("Nhập ID phòng ban cần sửa: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nhập tên mới muốn thay đổi: ");
            String newName = sc.nextLine();

            if (qlDepartment.updateDepartment(id, newName)) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Cập nhật thất bại.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
        }
    }

    private static void findByIdAndName() {
        try {
            System.out.print("Nhập ID cần tìm: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nhập tên cần tìm: ");
            String name = sc.nextLine();

            List<Department> list = qlDepartment.findByDepartmentIdAndName(id, name);
            showDepartment(list);
        } catch (NumberFormatException e) {
            System.out.println("ID nhập vào không đúng định dạng.");
        }
    }
}