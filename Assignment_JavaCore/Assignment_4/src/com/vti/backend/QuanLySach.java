package com.vti.backend;

import com.vti.entity.*;
import com.vti.entity.TaiLieu;
import java.util.ArrayList;
import java.util.Scanner;

public class QuanLySach {

    public void menu() {

        Scanner sc = new Scanner(System.in);
        ArrayList<TaiLieu> list = new ArrayList<>();

        while (true) {

            System.out.println("1. Thêm tài liệu");
            System.out.println("2. Xóa theo mã");
            System.out.println("3. Hiển thị");
            System.out.println("4. Tìm theo loại");
            System.out.println("5. Thoát");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":

                    boolean valid = false;

                    while (!valid) {

                        System.out.println("1. Sách");
                        System.out.println("2. Tạp chí");
                        System.out.println("3. Báo");

                        String type = sc.nextLine();

                        switch (type) {

                            case "1":

                                System.out.print("Mã tài liệu: ");
                                String maSach = sc.nextLine();

                                System.out.print("Tên NXB: ");
                                String nxbSach = sc.nextLine();

                                System.out.print("Số bản phát hành: ");
                                int sbphSach = Integer.parseInt(sc.nextLine());

                                System.out.print("Tên tác giả: ");
                                String tg = sc.nextLine();

                                System.out.print("Số trang: ");
                                int st = Integer.parseInt(sc.nextLine());

                                list.add(new Sach(maSach, nxbSach, sbphSach, tg, st));

                                valid = true;
                                break;

                            case "2":

                                System.out.print("Mã tài liệu: ");
                                String maTC = sc.nextLine();

                                System.out.print("Tên NXB: ");
                                String nxbTC = sc.nextLine();

                                System.out.print("Số bản phát hành: ");
                                int sbphTC = Integer.parseInt(sc.nextLine());

                                System.out.print("Số phát hành: ");
                                int sph = Integer.parseInt(sc.nextLine());

                                System.out.print("Tháng phát hành: ");
                                int tph = Integer.parseInt(sc.nextLine());

                                list.add(new TapChi(maTC, nxbTC, sbphTC, sph, tph));

                                valid = true;
                                break;

                            case "3":

                                System.out.print("Mã tài liệu: ");
                                String maBao = sc.nextLine();

                                System.out.print("Tên NXB: ");
                                String nxbBao = sc.nextLine();

                                System.out.print("Số bản phát hành: ");
                                int sbphBao = Integer.parseInt(sc.nextLine());

                                System.out.print("Ngày phát hành: ");
                                String ngay = sc.nextLine();

                                list.add(new Bao(maBao, nxbBao, sbphBao, ngay));

                                valid = true;
                                break;

                            default:
                                System.out.println("Nhập sai");
                        }
                    }

                    break;

                case "2":

                    if (list.isEmpty()) {
                        System.out.println("Danh sách trống");
                    } else {

                        System.out.print("Nhập mã cần xóa: ");
                        String maXoa = sc.nextLine();

                        boolean removed = false;

                        for (int i = 0; i < list.size(); i++) {

                            if (list.get(i).getMaTaiLieu().equalsIgnoreCase(maXoa)) {
                                list.remove(i);
                                removed = true;
                                i--;
                            }
                        }

                        if (removed) {
                            System.out.println("Đã xóa");
                        } else {
                            System.out.println("Không tìm thấy");
                        }
                    }

                    break;

                case "3":

                    if (list.isEmpty()) {
                        System.out.println("Danh sách trống");
                    } else {

                        for (TaiLieu tl : list) {
                            System.out.println(tl);
                        }
                    }

                    break;

                case "4":

                    if (list.isEmpty()) {
                        System.out.println("Danh sách trống");
                    } else {

                        System.out.println("1. Sách");
                        System.out.println("2. Tạp chí");
                        System.out.println("3. Báo");

                        String tk = sc.nextLine();

                        boolean found = false;

                        for (TaiLieu tl : list) {

                            switch (tk) {

                                case "1":
                                    if (tl.getLoaiTaiLieu().equals("Sách")) {
                                        System.out.println(tl);
                                        found = true;
                                    }
                                    break;

                                case "2":
                                    if (tl.getLoaiTaiLieu().equals("Tạp chí")) {
                                        System.out.println(tl);
                                        found = true;
                                    }
                                    break;

                                case "3":
                                    if (tl.getLoaiTaiLieu().equals("Báo")) {
                                        System.out.println(tl);
                                        found = true;
                                    }
                                    break;
                            }
                        }

                        if (!found) {
                            System.out.println("Không tìm thấy");
                        }
                    }

                    break;

                case "5":
                    return;

                default:
                    System.out.println("Nhập sai");
            }
        }
    }
}