package com.vti.entity;

public class Bao extends TaiLieu {

    private String ngayPhatHanh;

    public Bao() {
    }

    public Bao(String maTaiLieu, String tenNhaXuatBan,
               int soBanPhatHanh,
               String ngayPhatHanh) {

        super(maTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.ngayPhatHanh = ngayPhatHanh;
    }

    @Override
    public String getLoaiTaiLieu() {
        return "Báo";
    }

    @Override
    public String toString() {
        return "Báo -> " + super.toString() +
                ", Ngày phát hành: " + ngayPhatHanh;
    }
}