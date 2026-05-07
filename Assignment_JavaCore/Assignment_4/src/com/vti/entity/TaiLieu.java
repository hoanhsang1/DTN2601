package com.vti.entity;


public abstract class TaiLieu {
    protected String maTaiLieu;
    protected String tenNhaXuatBan;
    protected int soBanPhatHanh;

    public TaiLieu() {
    }

    public TaiLieu(String maTaiLieu, String tenNhaXuatBan, int soBanPhatHanh) {
        this.maTaiLieu = maTaiLieu;
        this.tenNhaXuatBan = tenNhaXuatBan;
        this.soBanPhatHanh = soBanPhatHanh;
    }

    public String getMaTaiLieu() {
        return maTaiLieu;
    }

    public void setMaTaiLieu(String maTaiLieu) {
        this.maTaiLieu = maTaiLieu;
    }

    public abstract String getLoaiTaiLieu();

    @Override
    public String toString() {
        return "Mã tài liệu: " + maTaiLieu +
                ", Nhà xuất bản: " + tenNhaXuatBan +
                ", Số bản phát hành: " + soBanPhatHanh;
    }
}

