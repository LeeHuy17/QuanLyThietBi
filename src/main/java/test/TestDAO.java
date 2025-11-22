package test;

import dao.ThietBiDAO;
import dao.NhapKhoDAO;
import dao.XuatKhoDAO;
import dao.ThongKeDAO;

import model.ThietBi;
import model.NhapKho;
import model.XuatKho;

import util.JsonUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {

    public static void main(String[] args) {

        // 1. Thêm thiết bị và lấy ID
        int idThietBi = testThemThietBi();

        if (idThietBi > 0) {
            testNhapKho(idThietBi);
            testXuatKho(idThietBi);
            testTonKho(idThietBi);
            testLichSuJson(idThietBi);
        } else {
            System.out.println("Không có thiết bị để test nhập/xuất kho!");
        }
    }

    // 1. Test thêm thiết bị
    public static int testThemThietBi() {
        System.out.println("=== TEST THÊM THIẾT BỊ ===");

        ThietBiDAO tbDAO = new ThietBiDAO();

        ThietBi tb = new ThietBi();
        tb.setTenThietBi("Máy In HP 2130");
        tb.setIdLoaiThietBi(1);      // đảm bảo idLoaiThietBi tồn tại trong DB
        tb.setTinhTrang("Đang dùng");
        tb.setMoTa("Thiết bị test");

        // insertReturnId() là hàm DAO trả về ID vừa thêm
        int idThietBi = tbDAO.insertReturnId(tb);

        if (idThietBi > 0) {
            System.out.println("Thêm thành công, idThietBi = " + idThietBi);
        } else {
            System.out.println("Thêm thất bại!");
        }

        return idThietBi;
    }

    // 2. Test nhập kho
    public static void testNhapKho(int idThietBi) {
        System.out.println("=== TEST NHẬP KHO ===");

        NhapKhoDAO nkDAO = new NhapKhoDAO();

        NhapKho nk = new NhapKho();
        nk.setIdThietBi(idThietBi);
        nk.setSoLuong(5);
        nk.setNgayNhap(LocalDate.now());
        nk.setNguoiNhap("Admin");

        boolean result = nkDAO.insert(nk);
        System.out.println("Kết quả nhập kho: " + result);
    }

    // 3. Test xuất kho
    public static void testXuatKho(int idThietBi) {
        System.out.println("=== TEST XUẤT KHO ===");

        XuatKhoDAO xkDAO = new XuatKhoDAO();

        XuatKho xk = new XuatKho();
        xk.setIdThietBi(idThietBi);
        xk.setSoLuong(2);
        xk.setNgayXuat(LocalDate.now());
        xk.setNguoiXuat("Admin");

        boolean result = xkDAO.insert(xk);
        System.out.println("Kết quả xuất kho: " + result);
    }

    // 4. Test tồn kho
    public static void testTonKho(int idThietBi) {
        System.out.println("=== TEST TỒN KHO ===");

        ThongKeDAO tkDAO = new ThongKeDAO();

        int ton = tkDAO.tonKho(idThietBi);
        System.out.println("Số lượng tồn: " + ton);
    }

    // 5. Test ghi JSON lịch sử
    public static void testLichSuJson(int idThietBi) {
        System.out.println("=== TEST JSON LỊCH SỬ ===");

        List<String> lichSu = new ArrayList<>();
        lichSu.add("Thiết bị " + idThietBi + ": Nhập 5 sản phẩm ngày " + LocalDate.now());
        lichSu.add("Thiết bị " + idThietBi + ": Xuất 2 sản phẩm ngày " + LocalDate.now());

        JsonUtil.writeListToFile(lichSu, "lich_su_kho.json");
        System.out.println("Đã ghi lịch sử vào file JSON!");
    }
}
