package com.vti.backend;

import com.vti.entity.CanBo;
import com.vti.entity.CongNhan;
import com.vti.entity.KySu;
import com.vti.entity.NhanVien;

import java.util.ArrayList;
import java.util.Scanner;

public class QLCB {
    public void menu() {
        System.out.println("1. Thêm mới cán bộ.");
        System.out.println("2. Tìm kiếm theo họ tên");
        System.out.println("3. Hiện thị thông tin về danh sách các cán bộ.");
        System.out.println("4. Nhập vào tên của cán bộ và delete cán bộ đó");
        System.out.println("5. Thoát khỏi chương trình.");

        Scanner sc = new Scanner(System.in);
        ArrayList<CanBo> list = new ArrayList<>();
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    boolean valid = false;

                    while (!valid) {
                        System.out.println("Chọn loại cán bộ:");
                        System.out.println("1. Công nhân");
                        System.out.println("2. Kỹ sư");
                        System.out.println("3. Nhân viên");

                        String type = sc.nextLine();

                        switch (type) {
                            case "1":
                                CongNhan cn = new CongNhan();
                                cn.themCongNhan();
                                list.add(cn);
                                valid = true;
                                break;
                            case "2":
                                KySu ks = new KySu();
                                ks.themKySu();
                                list.add(ks);
                                valid = true;
                                break;
                            case "3":
                                NhanVien nv = new NhanVien();
                                nv.themNhanVien();
                                list.add(nv);
                                valid = true;
                                break;
                            default:
                                System.out.println("Nhập sai, chọn lại (1-3)");
                        }
                    }
                    break;
                case "2":
                    if (list.isEmpty()) {
                        System.out.println("Danh sách trống");
                    } else {
                        String hoten = sc.nextLine();
                        boolean check = false;
                        for (CanBo c : list) {
                            if (hoten.equalsIgnoreCase(c.getHoTen())) {
                                System.out.println(c);
                                check = true;
                                break;
                            }
                        }
                        if (!check) {
                            System.out.println("Không có cán bộ nào tên " + hoten);
                        }
                    }

                    break;
                case "3":
                    if (list.isEmpty()) {
                        System.out.println("Danh sách trống");
                    } else {
                        for (CanBo cb : list) {
                            System.out.println(cb);
                        }
                    }
                    break;
                case "4":
                    if (list.isEmpty()) {
                        System.out.println("Danh sách trống");
                    } else {
                        System.out.print("Nhập họ tên cần xóa: ");
                        String nameDel = sc.nextLine();

                        boolean removed = false;

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getHoTen().equalsIgnoreCase(nameDel)) {
                                list.remove(i);
                                i--; // tránh bỏ sót phần tử phía sau
                                removed = true;
                            }
                        }

                        if (removed) {
                            System.out.println("Đã xóa");
                        } else {
                            System.out.println("Không tìm thấy");
                        }
                    }

                    break;
                case "5":
                    return;

            }
        }
    }
}
