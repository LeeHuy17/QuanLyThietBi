package dao.structured;

import db.DBUtil;
import model.LichSuTinhTrang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LichSuTinhTrangDAO {

    // Thêm bản ghi lịch sử
    public boolean add(LichSuTinhTrang ls) {
        String sql = "INSERT INTO LichSuTinhTrang (idThietBi, tinhTrangCu, tinhTrangMoi, ngayCapNhat, ghiChu) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ls.getIdThietBi());
            ps.setString(2, ls.getTinhTrangCu());
            ps.setString(3, ls.getTinhTrangMoi());
            ps.setDate(4, java.sql.Date.valueOf(ls.getNgayCapNhat()));
            ps.setString(5, ls.getGhiChu());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean insert(LichSuTinhTrang ls) {
        String sql = "INSERT INTO LichSuTinhTrang(idThietBi, tinhTrangCu, tinhTrangMoi, ngayCapNhat, ghiChu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ls.getIdThietBi());
            ps.setString(2, ls.getTinhTrangCu());
            ps.setString(3, ls.getTinhTrangMoi());
            ps.setDate(4, Date.valueOf(ls.getNgayCapNhat()));
            ps.setString(5, ls.getGhiChu());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
