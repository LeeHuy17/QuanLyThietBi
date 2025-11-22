package model;

public class ThietBi {
    private int idThietBi;
    private String tenThietBi;
    private int idLoaiThietBi;
    private String tinhTrang;
    private String moTa;

    // Constructor, getters và setters
    public int getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(int idThietBi) {
        this.idThietBi = idThietBi;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public int getIdLoaiThietBi() {
        return idLoaiThietBi;
    }

    public void setIdLoaiThietBi(int idLoaiThietBi) {
        this.idLoaiThietBi = idLoaiThietBi;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
