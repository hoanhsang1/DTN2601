package com.vti.entity;

public class Sach extends TaiLieu {

    private String tenTacGia;
    private int soTrang;

    public Sach() {
    }

    public Sach(String maTaiLieu, String tenNhaXuatBan,
                int soBanPhatHanh,
                String tenTacGia, int soTrang) {

        super(maTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.tenTacGia = tenTacGia;
        this.soTrang = soTrang;
    }

    @Override
    public String getLoaiTaiLieu() {
        return "Sách";
    }

    @Override
    public String toString() {
        return "Sách -> " + super.toString() +
                ", Tác giả: " + tenTacGia +
                ", Số trang: " + soTrang;
    }
}