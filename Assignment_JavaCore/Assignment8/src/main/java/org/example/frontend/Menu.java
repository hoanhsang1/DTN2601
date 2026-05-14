package org.example.frontend;

import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("=== Mời bạn chọn chức năng ===");
            System.out.println("1. Làm việc với department");
            System.out.println("2. Làm việc với position");
            System.out.println("3. Làm việc với account");
            System.out.println("0. Thoát");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    new DepartmentFunction().run();
                    break;
                case "2":
                    new PositionFunction().run();
                    break;
                case "3":
                    new AccountFunction().run();
                    break;
                case "0":
                    System.out.println("Tạm biệt!");
                    return;
                default:
                    System.out.println("Mời chọn lại");
            }
        }
    }
}
