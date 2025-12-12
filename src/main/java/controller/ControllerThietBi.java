package controller;

import dao.structured.ThietBiDAO;
import dao.structured.LichSuTinhTrangDAO;
import dao.unstructured.LichSuTinhTrangJsonDAO;
import dao.structured.TonKhoDAO;
import model.ThietBi;
import model.LichSuTinhTrang;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ControllerThietBi {
    private ThietBiDAO thietBiDAO = new ThietBiDAO();
    private LichSuTinhTrangDAO lichSuDAO = new LichSuTinhTrangDAO();
    private LichSuTinhTrangJsonDAO lichSuJsonDAO = new LichSuTinhTrangJsonDAO();

    private TonKhoDAO tonKhoDAO = new TonKhoDAO();

    public String addThietBi(ThietBi tb) {

        // --- Validate dữ liệu ---
        if (tb == null) return "Dữ liệu thiết bị rỗng!";
        if (tb.getTenThietBi() == null || tb.getTenThietBi().trim().isEmpty())
            return "Tên thiết bị không được để trống!";
        if (tb.getIdLoaiThietBi() <= 0)
            return "Loại thiết bị không hợp lệ!";
        if (tb.getTinhTrang() == null || tb.getTinhTrang().trim().isEmpty())
            return "Tình trạng không được để trống!";

        if (thietBiDAO.existsByNameAndLoai(tb.getTenThietBi(), tb.getIdLoaiThietBi()))
            return "Tên thiết bị đã tồn tại trong loại này!";

        // --- Thêm thiết bị ---
        int newId = thietBiDAO.insertAndGetId(tb); // Hàm mới: insert và trả về idThietBi
        if (newId <= 0) return "Lỗi khi thêm thiết bị!";

        // --- Tạo tồn kho với soLuongTon = 0 ---
        boolean tonOk = tonKhoDAO.insert(newId, 0); // thêm bản ghi vào TonKho
        if (!tonOk) return "Lỗi khi tạo bản ghi tồn kho!";

        return "SUCCESS";
    }


    // Cập nhật thiết bị
    public String updateThietBi(ThietBi tb) {

        if (tb == null) return "Dữ liệu rỗng!";
        if (tb.getIdThietBi() <= 0) return "ID thiết bị không hợp lệ!";
        if (tb.getTenThietBi() == null || tb.getTenThietBi().trim().isEmpty())
            return "Tên thiết bị không hợp lệ!";

        ThietBi old = thietBiDAO.findById(tb.getIdThietBi());
        if (old == null) return "Thiết bị không tồn tại!";

        boolean ok = thietBiDAO.update(tb);

        // Nếu đổi tình trạng → ghi log
        if (ok && !old.getTinhTrang().equals(tb.getTinhTrang())) {
            LichSuTinhTrang ls = new LichSuTinhTrang(
                    tb.getIdThietBi(),
                    old.getTinhTrang(),
                    tb.getTinhTrang(),
                    LocalDate.now(),
                    tb.getMoTa()
            );
            lichSuDAO.insert(ls);
        }

        return ok ? "SUCCESS" : "Lỗi khi cập nhật thiết bị!";
    }

    // Xóa thiết bị
    public String deleteThietBi(int id) {

        if (id <= 0) return "ID không hợp lệ!";
        if (thietBiDAO.findById(id) == null)
            return "Thiết bị không tồn tại!";

        boolean ok = thietBiDAO.delete(id);
        return ok ? "SUCCESS" : "Lỗi khi xóa thiết bị!";
    }

    // Danh sách thiết bị
    public List<ThietBi> getAll() {
        return thietBiDAO.getAll();
    }

    // Tìm kiếm thiết bị
    public List<ThietBi> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) return new ArrayList<>();
        keyword = keyword.toLowerCase();
        List<ThietBi> result = new ArrayList<>();

        // Lấy danh sách thiết bị từ DAO
        List<ThietBi> all = thietBiDAO.getAll();

        for (ThietBi tb : all) {
            if (tb.getTenThietBi().toLowerCase().contains(keyword) ||
                    tb.getMoTa().toLowerCase().contains(keyword)) {
                result.add(tb);
            }
        }
        return result;
    }

    // Cập nhật tình trạng và ghi log
    public String capNhatTinhTrang(int idTb, String tinhTrangMoi, String ghiChu) {
        System.out.println("=== capNhatTinhTrang START ===");

        ThietBi tb = thietBiDAO.findById(idTb);
        System.out.println("tb=" + tb);

        if (tb == null) return "Thiết bị không tồn tại!";

        String tinhTrangCu = tb.getTinhTrang();
        System.out.println("Cu=" + tinhTrangCu + " | Moi=" + tinhTrangMoi);

        boolean ok = thietBiDAO.updateTinhTrang(idTb, tinhTrangMoi);
        System.out.println("updateTinhTrang=" + ok);

        LichSuTinhTrang ls = new LichSuTinhTrang(
                idTb,
                tinhTrangCu,
                tinhTrangMoi,
                LocalDate.now(),
                ghiChu
        );
        System.out.println("LS new=" + ls);

        try {
            lichSuDAO.insert(ls);
            lichSuJsonDAO.insert(ls);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Lỗi ghi lịch sử JSON/SQL";
        }

        System.out.println("=== capNhatTinhTrang END ===");
        return "SUCCESS";
    }
}
