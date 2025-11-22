package model;

import java.time.LocalDate;

public class NhapKho {
    private int idNhap;         // idPK
    private int idThietBi;      // FK
    private int soLuong;
    private LocalDate ngayNhap;
    private String nguoiNhap;

    // Constructor mặc định
    public NhapKho() {}

    // Constructor đầy đủ
    public NhapKho(int idNhap, int idThietBi, int soLuong, LocalDate ngayNhap, String nguoiNhap) {
        this.idNhap = idNhap;
        this.idThietBi = idThietBi;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
        this.nguoiNhap = nguoiNhap;
    }

    // Getters và Setters
    public int getIdNhap() {
        return idNhap;
    }

    public void setIdNhap(int idNhap) {
        this.idNhap = idNhap;
    }

    public int getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(int idThietBi) {
        this.idThietBi = idThietBi;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getNguoiNhap() {
        return nguoiNhap;
    }

    public void setNguoiNhap(String nguoiNhap) {
        this.nguoiNhap = nguoiNhap;
    }
}
