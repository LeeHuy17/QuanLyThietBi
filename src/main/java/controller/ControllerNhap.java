package controller;

import dao.structured.NhapKhoDAO;
import dao.structured.TonKhoDAO;
import dao.structured.ThietBiDAO;
import dao.structured.LichSuTinhTrangDAO;
import model.NhapKho;
import model.ThietBi;
import model.LichSuTinhTrang;

import java.time.LocalDate;

public class ControllerNhap {

    private NhapKhoDAO nhapDAO = new NhapKhoDAO();
    private TonKhoDAO tonKhoDAO = new TonKhoDAO();
    private ThietBiDAO thietBiDAO = new ThietBiDAO();
    private LichSuTinhTrangDAO lichSuDAO = new LichSuTinhTrangDAO();

    /**
     * Xử lý nhập kho theo luồng:
     * 1. Validate dữ liệu
     * 2. Kiểm tra thiết bị tồn tại
     * 3. Thêm bản ghi nhập kho
     * 4. Tăng tồn kho
     * 5. Ghi log tình trạng nếu cần
     */
    public String nhapKho(NhapKho nk) {

        // 1. Validate cơ bản
        if (nk.getSoLuong() <= 0) {
            return "Số lượng nhập không hợp lệ!";
        }
        if (nk.getNgayNhap() == null) {
            return "Ngày nhập không được để trống!";
        }
        if (nk.getNguoiNhap() == null || nk.getNguoiNhap().trim().isEmpty()) {
            return "Người nhập không hợp lệ!";
        }

        // 2. Kiểm tra thiết bị có tồn tại không
        ThietBi tb = thietBiDAO.findById(nk.getIdThietBi());
        if (tb == null) {
            return "Thiết bị không tồn tại!";
        }

        // 3. Ghi vào bảng Nhập kho
        boolean okNhap = nhapDAO.insert(nk);
        if (!okNhap) {
            return "Lỗi khi ghi dữ liệu nhập kho!";
        }

        // 4. Tăng tồn kho
        boolean okTon = tonKhoDAO.increase(nk.getIdThietBi(), nk.getSoLuong());
        if (!okTon) {
            return "Lỗi khi cập nhật tồn kho!";
        } 

        // 5. Ghi lịch sử tình trạng (nếu là thiết bị mới)
        LichSuTinhTrang log = new LichSuTinhTrang(
                nk.getIdThietBi(),
                tb.getTinhTrang(),      // tinhTrangCu
                tb.getTinhTrang(),      // tinhTrangMoi (không đổi)
                LocalDate.now(),
                "Mới nhập kho, SL: " + nk.getSoLuong()
        );
        lichSuDAO.insert(log);

        return "SUCCESS";
    }
}
