package com.vti.frontend;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU CHÍNH =====");
            System.out.println("1. Quản lý Phòng ban (Department)");
            System.out.println("2. Quản lý Chức vụ (Position)");
            System.out.println("3. Quản lý Tài khoản (Account)");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    DepartmentFunction.run();
                    break;
                case "2":
                    PositionFunction.run();
                    break;
                case "3":
                    AccountFunction.run();
                    break;
                case "0":
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
            }
        }
    }
}
