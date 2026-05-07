package com.vti.entity;

public class TapChi extends TaiLieu {

    private int soPhatHanh;
    private int thangPhatHanh;

    public TapChi() {
    }

    public TapChi(String maTaiLieu, String tenNhaXuatBan,
                  int soBanPhatHanh,
                  int soPhatHanh, int thangPhatHanh) {

        super(maTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.soPhatHanh = soPhatHanh;
        this.thangPhatHanh = thangPhatHanh;
    }

    @Override
    public String getLoaiTaiLieu() {
        return "Tạp chí";
    }

    @Override
    public String toString() {
        return "Tạp chí -> " + super.toString() +
                ", Số phát hành: " + soPhatHanh +
                ", Tháng phát hành: " + thangPhatHanh;
    }
}
