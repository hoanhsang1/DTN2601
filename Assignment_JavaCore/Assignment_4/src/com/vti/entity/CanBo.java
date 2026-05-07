package com.vti.entity;

import com.vti.Enum.Sex;

import java.util.Scanner;

public class CanBo {
    private String hoTen;
    private int tuoi;
    private Sex gioiTinh;
    private String diaChi;

    public CanBo() {
    }

    ;

    public CanBo(String hoTen, int tuoi, Sex gioiTinh, String diaChi) {
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
    }

    public void themCanBo() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập họ tên: ");
        hoTen = sc.nextLine();

        System.out.print("Nhập tuổi: ");
        tuoi = sc.nextInt();
        sc.nextLine();

        boolean lc = true;
        while (lc) {
            System.out.println("Chọn giới tính:");
            System.out.println("1. Nam");
            System.out.println("2. Nữ");
            System.out.println("3. Khác");

            String cho = sc.nextLine();
            switch (cho) {
                case "1":
                    gioiTinh = Sex.nam;
                    lc = false;
                    break;
                case "2":
                    gioiTinh = Sex.nữ;
                    lc = false;
                    break;
                case "3":
                    gioiTinh = Sex.khác;
                    lc = false;
                    break;
                default:
                    System.out.println("Vui lòng nhập 1-3");
            }
        }

        System.out.print("Nhập địa chỉ: ");
        diaChi = sc.nextLine();
    }

    public String getHoTen() {
        return hoTen;
    }

    public int getTuoi() {
        return tuoi;
    }

    public Sex getGioiTinh() {
        return gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public void setGioiTinh(Sex gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "Họ tên: " + hoTen +
                ", Tuổi: " + tuoi +
                ", Giới tính: " + gioiTinh +
                ", Địa chỉ: " + diaChi;
    }
}
