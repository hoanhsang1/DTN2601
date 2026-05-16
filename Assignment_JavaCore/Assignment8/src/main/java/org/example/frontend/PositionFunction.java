package org.example.frontend;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.example.backend.controller.PositionController;
import org.example.entity.Position;

public class PositionFunction {

    PositionController positionController = new PositionController();
    private final Scanner sc = new Scanner(System.in);

    private static final String BORDER     = "+-------+----------------+";
    private static final String HEADER_FMT = "| %-5s | %-14s |%n";
    private static final String ROW_FMT    = "| %-5s | %-14s |%n";

    public void run() {
        while (true) {
            System.out.println("\n========== QUẢN LÝ CHỨC VỤ ==========");
            System.out.println("1. Xem danh sách chức vụ");
            System.out.println("2. Tìm chức vụ theo ID");
            System.out.println("3. Tìm chức vụ theo tên");
            System.out.println("4. Chức vụ có nhiều nhân viên nhất");
            System.out.println("5. Chức vụ có ít nhân viên nhất");
            System.out.println("6. Thêm mới chức vụ");
            System.out.println("7. Cập nhật chức vụ");
            System.out.println("8. Xóa chức vụ");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": showPosition(positionController.findAll());             break;
                case "2": findById();                                             break;
                case "3": findByName();                                           break;
                case "4": showPosition(positionController.findMostEmployees());   break;
                case "5": showPosition(positionController.findLeastEmployees());  break;
                case "6": insertPosition();                                       break;
                case "7": updatePosition();                                       break;
                case "8": deletePosition();                                       break;
                case "0": return;
                default:  System.out.println("Lựa chọn không hợp lệ, thử lại.");
            }
        }
    }

    public void showPosition(List<Position> positions) {
        System.out.println(BORDER);
        System.out.printf(HEADER_FMT, "ID", "Tên chức vụ");
        System.out.println(BORDER);
        if (positions.isEmpty()) {
            System.out.printf(ROW_FMT, "", "Không tìm thấy");
        } else {
            for (Position p : positions) {
                System.out.printf(ROW_FMT, p.getId(), p.getName());
            }
        }
        System.out.println(BORDER);
    }

    private void findById() {
        System.out.print("Nhập ID chức vụ: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            Position pos = positionController.findById(id);
            showPosition(pos != null ? Collections.singletonList(pos) : Collections.emptyList());
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
        }
    }

    private void findByName() {
        System.out.print("Nhập tên chức vụ cần tìm (DEV / TEST / SCRUM_MASTER / PM): ");
        String name = sc.nextLine().trim();
        showPosition(positionController.findByName(name));
    }

    private void insertPosition() {
        System.out.println("Các giá trị hợp lệ: DEV, TEST, SCRUM_MASTER, PM");
        System.out.print("Nhập tên chức vụ: ");
        String name = sc.nextLine().trim();
        String error = positionController.create(name);
        if (error == null) {
            System.out.println("[OK] Thêm mới thành công.");
        } else {
            System.out.println("[Lỗi] " + error);
        }
    }

    private void updatePosition() {
        System.out.print("Nhập ID chức vụ cần cập nhật: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.println("Các giá trị hợp lệ: DEV, TEST, SCRUM_MASTER, PM");
            System.out.print("Nhập tên chức vụ mới: ");
            String name = sc.nextLine().trim();
            String error = positionController.update(id, name);
            if (error == null) {
                System.out.println("[OK] Cập nhật thành công.");
            } else {
                System.out.println("[Lỗi] " + error);
            }
        } catch (NumberFormatException e) {
            System.out.println("[Lỗi] ID phải là số nguyên.");
        }
    }

    private void deletePosition() {
        System.out.print("Nhập ID chức vụ cần xóa: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            String error = positionController.delete(id);
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
