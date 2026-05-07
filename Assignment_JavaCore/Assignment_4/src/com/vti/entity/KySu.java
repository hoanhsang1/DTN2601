package com.vti.entity;

import com.vti.Enum.Sex;

import java.util.Scanner;

public class KySu extends CanBo {
    private String nghanhDaoTao;

    public KySu() {
        super();
    }

    public KySu(String hoTen, int tuoi, Sex gioiTinh, String diaChi, String nghanhDaoTao) {
        super(hoTen, tuoi, gioiTinh, diaChi);
        this.nghanhDaoTao = nghanhDaoTao;
    }

    public void themKySu() {
        super.themCanBo();

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập ngành đào tạo: ");
        nghanhDaoTao = sc.nextLine();
    }

    public String getNghanhDaoTao() {
        return nghanhDaoTao;
    }

    public void setNghanhDaoTao(String nghanhDaoTao) {
        this.nghanhDaoTao = nghanhDaoTao;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Ngành đào tạo: " + nghanhDaoTao;
    }
}
