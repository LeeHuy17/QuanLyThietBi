package controller;

import dao.structured.XuatKhoDAO;
import dao.structured.TonKhoDAO;
import dao.structured.ThietBiDAO;
import dao.structured.LichSuTinhTrangDAO;

import model.XuatKho;
import model.ThietBi;
import model.LichSuTinhTrang;

import java.time.LocalDate;

public class ControllerXuat {

    private final XuatKhoDAO xuatDAO = new XuatKhoDAO();
    private final TonKhoDAO tonKhoDAO = new TonKhoDAO();
    private final ThietBiDAO thietBiDAO = new ThietBiDAO();
    private final LichSuTinhTrangDAO lichSuDAO = new LichSuTinhTrangDAO();

    // Xử lý nghiệp vụ Xuất kho
    public boolean xuatKho(XuatKho xk) {
        try {

            // ==================== 1. VALIDATE DỮ LIỆU ĐẦU VÀO ====================
            if (!validate(xk)) {
                System.err.println("Dữ liệu xuất kho không hợp lệ!");
                return false;
            }

            // ==================== 2. KIỂM TRA TỒN KHO ====================
            int tonHienTai = tonKhoDAO.getSoLuongTon(xk.getIdThietBi());

            if (tonHienTai <= 0) {
                System.err.println("Thiết bị không còn hàng trong kho.");
                return false;
            }
            if (xk.getSoLuong() > tonHienTai) {
                System.err.println("Không đủ số lượng tồn. Tồn: " + tonHienTai);
                return false;
            }

            // ==================== 3. GHI XUẤT KHO VÀO DATABASE ====================
            boolean isInserted = xuatDAO.insert(xk);
            if (!isInserted) {
                System.err.println("Lỗi khi ghi phiếu xuất kho.");
                return false;
            }

            // ==================== 4. TRỪ TỒN KHO ====================
            tonKhoDAO.decrease(xk.getIdThietBi(), xk.getSoLuong());

            // ==================== 5. CẬP NHẬT TÌNH TRẠNG THIẾT BỊ ====================
            ThietBi tb = thietBiDAO.findById(xk.getIdThietBi());
            if (tb == null) {
                System.err.println("Không tìm thấy thiết bị.");
                return false;
            }

            String tinhTrangCu = tb.getTinhTrang();
            String tinhTrangMoi = tinhTrangCu;

            // Nếu xuất xong mà hết hàng → chuyển trạng thái
            if (tonHienTai - xk.getSoLuong() <= 0) {
                tinhTrangMoi = "Hết hàng";
                thietBiDAO.updateTinhTrang(xk.getIdThietBi(), tinhTrangMoi);
            }

            // ==================== 6. GHI LỊCH SỬ TÌNH TRẠNG ====================
            LichSuTinhTrang log = new LichSuTinhTrang(
                    xk.getIdThietBi(),
                    tinhTrangCu,
                    tinhTrangMoi,
                    LocalDate.now(),
                    "Xuất " + xk.getSoLuong() + " thiết bị"
            );
            lichSuDAO.add(log);

            // ==================== DONE ====================
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    // Validate dữ liệu xuất kho
    private boolean validate(XuatKho xk) {

        if (xk == null) return false;

        if (xk.getIdThietBi() <= 0) return false;

        if (xk.getSoLuong() <= 0) return false;

        if (xk.getNguoiXuat() == null || xk.getNguoiXuat().trim().isEmpty())
            return false;

        if (xk.getNgayXuat() == null) return false;

        return true;
    }
}
