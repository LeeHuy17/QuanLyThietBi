package model;

public class LichSuTinhTrang {
    private String maThietBi;
    private String tenThietBi;
    private String ngayBaoLoi;
    private String moTaLoi;
    private String tinhTrang;

    public LichSuTinhTrang() {
    }

    public LichSuTinhTrang(String maThietBi, String tenThietBi,
                           String ngayBaoLoi, String moTaLoi, String tinhTrang) {
        this.maThietBi = maThietBi;
        this.tenThietBi = tenThietBi;
        this.ngayBaoLoi = ngayBaoLoi;
        this.moTaLoi = moTaLoi;
        this.tinhTrang = tinhTrang;
    }

    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getNgayBaoLoi() {
        return ngayBaoLoi;
    }

    public void setNgayBaoLoi(String ngayBaoLoi) {
        this.ngayBaoLoi = ngayBaoLoi;
    }

    public String getMoTaLoi() {
        return moTaLoi;
    }

    public void setMoTaLoi(String moTaLoi) {
        this.moTaLoi = moTaLoi;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
