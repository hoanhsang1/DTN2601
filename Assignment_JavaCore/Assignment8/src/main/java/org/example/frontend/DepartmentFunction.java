package org.example.frontend;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.example.backend.controller.DepartmentController;
import org.example.entity.Department;

public class DepartmentFunction {

    DepartmentController departmentController = new DepartmentController();
    private final Scanner sc = new Scanner(System.in);

    // độ rộng cột
    private static final String BORDER     = "+-------+------------------------+";
    private static final String HEADER_FMT = "| %-5s | %-22s |%n";
    private static final String ROW_FMT    = "| %-5s | %-22s |%n";

    public void run() {
        while (true) {
            System.out.println("\n========== QUẢN LÝ PHÒNG BAN ==========");
            System.out.println("1. Xem danh sách phòng ban");
            System.out.println("2. Tìm phòng ban theo ID");
            System.out.println("3. Tìm phòng ban theo tên");
            System.out.println("4. Phòng ban có nhiều nhân viên nhất");
            System.out.println("5. Phòng ban có ít nhân viên nhất");
            System.out.println("6. Thêm mới phòng ban");
            System.out.println("7. Cập nhật phòng ban");
            System.out.println("8. Xóa phòng ban");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": showDepartment(departmentController.findAll());           break;
                case "2": findById();                                               break;
                case "3": findByName();                                             break;
                case "4": showDepartment(departmentController.findMostEmployees()); break;
                case "5": showDepartment(departmentController.findLeastEmployees());break;
                case "6": insertDepartment();                                       break;
                case "7": updateDepartment();                                       break;
                case "8": deleteDepartment();                                       break;
                case "0": return;
                default:  System.out.println("Lựa chọn không hợp lệ, thử lại.");
            }
        }
    }

    public void showDepartment(List<Department> departments) {
        System.out.println(BORDER);
        System.out.printf(HEADER_FMT, "ID", "Tên phòng ban");
        System.out.println(BORDER);
        if (departments.isEmpty()) {
            System.out.printf(ROW_FMT, "", "Không tìm thấy dữ liệu");
        } else {
            for (Department d : departments) {
                System.out.printf(ROW_FMT, d.getId(), d.getName());
            }
        }
        System.out.println(BORDER);
    }

    private void findById() {
        System.out.print("Nhập ID phòng ban: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            Department dep = departmentController.findById(id);
            showDepartment(dep != null ? Collections.singletonList(dep) : Collections.emptyList());
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ.");
        }
    }

    private void findByName() {
        System.out.print("Nhập tên phòng ban cần tìm: ");
        String name = sc.nextLine().trim();
        showDepartment(departmentController.findByName(name));
    }

    private void insertDepartment() {
        System.out.print("Nhập tên phòng ban mới: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Tên không được để trống."); return; }
        System.out.println(departmentController.create(name) ? "Thêm mới thành công." : "Thêm mới thất bại.");
    }

    private void updateDepartment() {
        System.out.print("Nhập ID phòng ban cần cập nhật: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Nhập tên mới: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) { System.out.println("Tên không được để trống."); return; }
            System.out.println(departmentController.update(id, name) ? "Cập nhật thành công." : "Cập nhật thất bại.");
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ.");
        }
    }

    private void deleteDepartment() {
        System.out.print("Nhập ID phòng ban cần xóa: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.println(departmentController.delete(id) ? "Xóa thành công." : "Xóa thất bại.");
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ.");
        }
    }
}
