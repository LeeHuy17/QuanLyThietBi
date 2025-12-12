package commons;

import java.io.Serializable;

public class    Message implements Serializable {

    private Action action;
    private Object data;

    // Các trường dùng cho cập nhật tình trạng
    private Integer thietBiId;
    private String tinhTrangCu;
    private String tinhTrangMoi;
    private String ghiChu;
    private String keyword;

    // Constructor chuẩn (dùng khắp project)
    public Message(Action action, Object data) {
        this.action = action;
        this.data = data;
    }

    // Constructor dành riêng cho UPDATE_TINH_TRANG
    public Message(Action action, Integer thietBiId,
                   String tinhTrangCu, String tinhTrangMoi, String ghiChu) {
        this.action = action;
        this.thietBiId = thietBiId;
        this.tinhTrangCu = tinhTrangCu;
        this.tinhTrangMoi = tinhTrangMoi;
        this.ghiChu = ghiChu;
    }

    // Getter
    public Action getAction() {
        return action;
    }

    public Object getData() {
        return data;
    }

    public Integer getThietBiId() {
        return thietBiId;
    }

    public String getTinhTrangCu() {
        return tinhTrangCu;
    }

    public String getTinhTrangMoi() {
        return tinhTrangMoi;
    }

    public String getGhiChu() {
        return ghiChu;
    }
    public String getKeyword() {
        return keyword;
    }

    // Setter
    public void setThietBiId(Integer thietBiId) {
        this.thietBiId = thietBiId;
    }

    public void setTinhTrangCu(String tinhTrangCu) {
        this.tinhTrangCu = tinhTrangCu;
    }

    public void setTinhTrangMoi(String tinhTrangMoi) {
        this.tinhTrangMoi = tinhTrangMoi;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
