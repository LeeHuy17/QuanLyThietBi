package model;

import java.time.LocalDate;

public class XuatKho {
    private int idXuat;         // idPK
    private int idThietBi;      // FK
    private int soLuong;
    private LocalDate ngayXuat;
    private String nguoiXuat;

    // Constructor mặc định
    public XuatKho() {}

    // Constructor đầy đủ
    public XuatKho(int idXuat, int idThietBi, int soLuong, LocalDate ngayXuat, String nguoiXuat) {
        this.idXuat = idXuat;
        this.idThietBi = idThietBi;
        this.soLuong = soLuong;
        this.ngayXuat = ngayXuat;
        this.nguoiXuat = nguoiXuat;
    }

    // Getters và Setters
    public int getIdXuat() {
        return idXuat;
    }

    public void setIdXuat(int idXuat) {
        this.idXuat = idXuat;
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

    public LocalDate getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(LocalDate ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getNguoiXuat() {
        return nguoiXuat;
    }

    public void setNguoiXuat(String nguoiXuat) {
        this.nguoiXuat = nguoiXuat;
    }
}
