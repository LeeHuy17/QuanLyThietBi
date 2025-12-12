package model;

import java.io.Serializable;
import java.time.LocalDate;

public class LichSuTinhTrang implements Serializable {
    private int idThietBi;
    private String tinhTrangCu;
    private String tinhTrangMoi;
    private LocalDate ngayCapNhat;
    private String ghiChu;

    public LichSuTinhTrang() {
    }

    public LichSuTinhTrang(int idThietBi, String tinhTrangCu, String tinhTrangMoi,
                           LocalDate ngayCapNhat, String ghiChu) {
        this.idThietBi = idThietBi;
        this.tinhTrangCu = tinhTrangCu;
        this.tinhTrangMoi = tinhTrangMoi;
        this.ngayCapNhat = ngayCapNhat;
        this.ghiChu = ghiChu;
    }

    public int getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(int idThietBi) {
        this.idThietBi = idThietBi;
    }

    public String getTinhTrangCu() {
        return tinhTrangCu;
    }

    public void setTinhTrangCu(String tinhTrangCu) {
        this.tinhTrangCu = tinhTrangCu;
    }

    public String getTinhTrangMoi() {
        return tinhTrangMoi;
    }

    public void setTinhTrangMoi(String tinhTrangMoi) {
        this.tinhTrangMoi = tinhTrangMoi;
    }

    public LocalDate getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDate ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}