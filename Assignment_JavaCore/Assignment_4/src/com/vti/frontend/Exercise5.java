package com.vti.frontend;

import com.vti.backend.QLCB;
import com.vti.backend.QuanLySach;

import java.util.Scanner;

public class Exercise5 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("===== EXERCISE 5 =====");
            System.out.println("1. Quản lý cán bộ");
            System.out.println("2. Quản lý tài liệu");
            System.out.println("3. Thoát");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":

                    QLCB qlcb = new QLCB();
                    qlcb.menu();

                    break;

                case "2":

                    QuanLySach quanLySach = new QuanLySach();
                    quanLySach.menu();

                    break;

                case "3":

                    return;

                default:
                    System.out.println("Nhập sai");
            }
        }
    }
}