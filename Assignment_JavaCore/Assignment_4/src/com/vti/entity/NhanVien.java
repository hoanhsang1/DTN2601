package com.vti.entity;

import com.vti.Enum.Sex;

import java.util.Scanner;

public class NhanVien extends CanBo {
    private String congViec;

    public NhanVien() {
        super();
    }

    public NhanVien(String hoTen, int tuoi, Sex gioiTinh, String diaChi, String congViec) {
        super(hoTen, tuoi, gioiTinh, diaChi);
        this.congViec = congViec;
    }

    public void themNhanVien() {
        super.themCanBo();

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập công việc: ");
        congViec = sc.nextLine();
    }

    public String getCongViec() {
        return congViec;
    }

    public void setCongViec(String congViec) {
        this.congViec = congViec;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Công việc: " + congViec;
    }
}
