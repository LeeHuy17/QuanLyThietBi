package model;

import java.io.Serializable;

public class ThietBi implements Serializable {
    private int idThietBi;
    private String tenThietBi;
    private int idLoaiThietBi;
    private String tinhTrang;
    private String moTa;
    private int soLuongTon; // số lượng tồn từ bảng TonKho

    public ThietBi() {}

    // 1) Constructor đầy đủ (id + tất cả trường)
    public ThietBi(int idThietBi, String tenThietBi, int idLoaiThietBi, String tinhTrang, String moTa, int soLuongTon) {
        this.idThietBi = idThietBi;
        this.tenThietBi = tenThietBi;
        this.idLoaiThietBi = idLoaiThietBi;
        this.tinhTrang = tinhTrang;
        this.moTa = moTa;
        this.soLuongTon = soLuongTon;
    }

    // 2) Constructor dùng khi đọc từ DB nhưng không có soLuongTon (nếu cần)
    public ThietBi(int idThietBi, String tenThietBi, int idLoaiThietBi, String tinhTrang, String moTa) {
        this(idThietBi, tenThietBi, idLoaiThietBi, tinhTrang, moTa, 0);
    }

    // 3) Constructor thêm mới (không có id, không có soLuongTon)
    public ThietBi(String tenThietBi, int idLoaiThietBi, String tinhTrang, String moTa) {
        this.tenThietBi = tenThietBi;
        this.idLoaiThietBi = idLoaiThietBi;
        this.tinhTrang = tinhTrang;
        this.moTa = moTa;
        this.soLuongTon = 0;
    }

    // 4) Constructor cập nhật theo id + đầy đủ (id trước)
    public ThietBi(int idThietBi, String tenThietBi, int idLoaiThietBi, String tinhTrang, String moTa, boolean dummy) {
        // dummy chỉ để khác chữ ký, có thể  dùng constructor (int, String, int, String, String) ở trên
        this(idThietBi, tenThietBi, idLoaiThietBi, tinhTrang, moTa, 0);
    }

    // 5) Constructor chỉ để cập nhật trạng thái (id + tinhTrang)
    public ThietBi(int idThietBi, String tinhTrang) {
        this.idThietBi = idThietBi;
        this.tinhTrang = tinhTrang;
    }

    // Getter / Setter
    public int getIdThietBi() { return idThietBi; }
    public void setIdThietBi(int idThietBi) { this.idThietBi = idThietBi; }

    public String getTenThietBi() { return tenThietBi; }
    public void setTenThietBi(String tenThietBi) { this.tenThietBi = tenThietBi; }

    public int getIdLoaiThietBi() { return idLoaiThietBi; }
    public void setIdLoaiThietBi(int idLoaiThietBi) { this.idLoaiThietBi = idLoaiThietBi; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }

    @Override
    public String toString() {
        return tenThietBi + " (Loại: " + idLoaiThietBi + ")";
    }
}
