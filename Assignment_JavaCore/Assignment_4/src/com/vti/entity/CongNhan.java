package com.vti.entity;

import com.vti.Enum.Level;
import com.vti.Enum.Sex;

import java.util.Scanner;

public class CongNhan extends CanBo {
    private Level capCongNhan;

    public CongNhan() {
        super();
    }

    ;

    public CongNhan(String hoTen, int tuoi, Sex gioiTinh, String diaChi, Level capCongNhan) {
        super(hoTen, tuoi, gioiTinh, diaChi);
        this.capCongNhan = capCongNhan;
    }

    public void themCongNhan() {
        super.themCanBo(); // nhập hoTen, tuoi, gioiTinh, diaChi

        Scanner sc = new Scanner(System.in);
        boolean lc = true;

        while (lc) {
            System.out.println("Chọn cấp công nhân (1-10): ");
            String chon = sc.nextLine();

            switch (chon) {
                case "1":
                    capCongNhan = Level.LEVEL_1;
                    lc = false;
                    break;
                case "2":
                    capCongNhan = Level.LEVEL_2;
                    lc = false;
                    break;
                case "3":
                    capCongNhan = Level.LEVEL_3;
                    lc = false;
                    break;
                case "4":
                    capCongNhan = Level.LEVEL_4;
                    lc = false;
                    break;
                case "5":
                    capCongNhan = Level.LEVEL_5;
                    lc = false;
                    break;
                case "6":
                    capCongNhan = Level.LEVEL_6;
                    lc = false;
                    break;
                case "7":
                    capCongNhan = Level.LEVEL_7;
                    lc = false;
                    break;
                case "8":
                    capCongNhan = Level.LEVEL_8;
                    lc = false;
                    break;
                case "9":
                    capCongNhan = Level.LEVEL_9;
                    lc = false;
                    break;
                case "10":
                    capCongNhan = Level.LEVEL_10;
                    lc = false;
                    break;
                default:
                    System.out.println("Vui lòng nhập từ 1-10");
            }
        }
    }

    public void setCapCongNhan(Level capCongNhan) {
        this.capCongNhan = capCongNhan;
    }

    public Level getCapCongNhan() {
        return capCongNhan;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Cấp công nhân: " + capCongNhan;
    }
}
