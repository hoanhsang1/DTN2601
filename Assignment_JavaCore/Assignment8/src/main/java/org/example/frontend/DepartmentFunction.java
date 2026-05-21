package org.example.frontend;

import org.example.backend.controller.DepartmentController;
import org.example.entity.Department;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DepartmentFunction {

    DepartmentController departmentController = new DepartmentController();
    private final Scanner sc = new Scanner(System.in);

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
            System.out.println("9. Import từ file csv phòng ban");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": showDepartment(departmentController.findAll());            break;
                case "2": findById();                                                break;
                case "3": findByName();                                              break;
                case "4": showDepartment(departmentController.findMostEmployees());  break;
                case "5": showDepartment(departmentController.findLeastEmployees()); break;
                case "6": insertDepartment();                                        break;
                case "7": updateDepartment();                                        break;
                case "8": deleteDepartment();                                        break;
                case "9": importDepartmentFromCSV();                                        break;
                case "0": return;
                default:  System.out.println("Lựa chọn không hợp lệ, thử lại.");
            }
        }
    }
//    "D:\User\downloads\Assignment_VTi\a.csv"
    private void importDepartmentFromCSV() {
        System.out.println("========== IMPORTANT DEPARTMENT ==========");
        System.out.println("Nhập địa chỉ lưu file Import");
        String fileName = sc.nextLine();
        departmentController.importDepartmentFromCSV(fileName);
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
            System.out.println("[Lỗi] ID phải là số nguyên.");
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
        String error = departmentController.create(name);
        if (error == null) {
            System.out.println("[OK] Thêm mới thành công.");
        } else {
            System.out.println("[Lỗi] " + error);
        }
    }

    private void updateDepartment() {
        System.out.print("Nhập ID phòng ban cần cập nhật: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Nhập tên mới: ");
            String name = sc.nextLine().trim();
            String error = departmentController.update(id, name);
            if (error == null) {
                System.out.println("[OK] Cập nhật thành công.");
            } else {
                System.out.println("[Lỗi] " + error);
            }
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
        }
    }

    private void deleteDepartment() {
        System.out.print("Nhập ID phòng ban cần xóa: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            String error = departmentController.delete(id);
            if (error == null) {
                System.out.println("[OK] Xóa thành công.");
            } else {
                System.out.println("[Lỗi] " + error);
            }
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
        }
    }
}
