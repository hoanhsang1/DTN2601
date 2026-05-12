package com.vti.frontend;

import com.vti.Enum.PositionName;
import com.vti.backend.QLPosition;
import com.vti.entity.Position;
import java.util.List;
import java.util.Scanner;

public class PositionFunction {
    private static final Scanner sc = new Scanner(System.in);
    private static final QLPosition qlPosition = new QLPosition();

    public static void run() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ CHỨC VỤ (POSITION) ===");
            System.out.println("1. Danh sách chức vụ");
            System.out.println("2. Thêm mới chức vụ");
            System.out.println("3. Cập nhật chức vụ theo ID");
            System.out.println("4. Xóa chức vụ theo ID");
            System.out.println("5. Xóa chức vụ theo tên");
            System.out.println("6. Tìm kiếm theo tên");
            System.out.println("7. Chức vụ đông nhân viên nhất");
            System.out.println("8. Chức vụ ít nhân viên nhất");
            System.out.println("9. Quay lại Menu chính");
            System.out.print("Mời bạn chọn: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    List<Position> list = qlPosition.getListPosition();
                    showPositionTable(list);
                    break;
                case "2":
                    insertPosition();
                    break;
                case "3":
                    updatePosition();
                    break;
                case "4":
                    deletePositionById();
                    break;
                case "5":
                    deletePositionByName();
                    break;
                case "6":
                    findByName();
                    break;
                case "7":
                    qlPosition.getPositionsWithMostAccounts();
                    break;
                case "8":
                    qlPosition.getPositionsWithLeastAccounts();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
            }
        }
    }

    private static void showPositionTable(List<Position> list) {
        if (list == null || list.isEmpty()) {
            System.out.println(">>> Không có dữ liệu chức vụ.");
            return;
        }
        System.out.println("+------+------------------+");
        System.out.printf("| %-4s | %-16s |%n", "ID", "Tên chức vụ");
        System.out.println("+------+------------------+");
        for (Position p : list) {
            System.out.printf("| %-4d | %-16s |%n", p.getPositionId(), p.getPositionName());
        }
        System.out.println("+------+------------------+");
    }

    private static void insertPosition() {
        System.out.println("Các chức vụ hợp lệ: DEV, TEST, SCRUM_MASTER, PM");
        System.out.print("Nhập tên chức vụ mới: ");
        String name = sc.nextLine().trim().toUpperCase();
        try {
            PositionName positionName = PositionName.valueOf(name);
            Position position = new Position();
            position.setPositionName(positionName);

            if (qlPosition.createPosition(position)) {
                System.out.println("Thêm mới thành công!");
            } else {
                System.out.println("Thêm mới thất bại.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Tên chức vụ không hợp lệ. Chỉ chấp nhận: DEV, TEST, SCRUM_MASTER, PM");
        }
    }

    private static void updatePosition() {
        try {
            System.out.print("Nhập ID chức vụ cần sửa: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.println("Các chức vụ hợp lệ: DEV, TEST, SCRUM_MASTER, PM");
            System.out.print("Nhập tên chức vụ mới: ");
            String name = sc.nextLine().trim().toUpperCase();

            PositionName positionName = PositionName.valueOf(name);
            Position position = new Position();
            position.setPositionId(id);
            position.setPositionName(positionName);

            if (qlPosition.updatePosition(position)) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Cập nhật thất bại (ID không tồn tại).");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
        } catch (IllegalArgumentException e) {
            System.out.println("Tên chức vụ không hợp lệ. Chỉ chấp nhận: DEV, TEST, SCRUM_MASTER, PM");
        }
    }

    private static void deletePositionById() {
        try {
            System.out.print("Nhập ID chức vụ cần xóa: ");
            int id = Integer.parseInt(sc.nextLine());

            if (qlPosition.deletePosition(id)) {
                System.out.println("Xóa thành công!");
            } else {
                System.out.println("Xóa thất bại (ID không tồn tại hoặc có ràng buộc nhân viên).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập ID là một con số.");
        }
    }

    private static void deletePositionByName() {
        System.out.print("Nhập tên chức vụ cần xóa (DEV/TEST/SCRUM_MASTER/PM): ");
        String name = sc.nextLine().trim().toUpperCase();

        if (qlPosition.deleteByName(name)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại (tên không tồn tại hoặc có ràng buộc nhân viên).");
        }
    }

    private static void findByName() {
        System.out.print("Nhập tên chức vụ cần tìm: ");
        String name = sc.nextLine().trim();

        List<Position> list = qlPosition.findByName(name);
        showPositionTable(list);
    }
}
