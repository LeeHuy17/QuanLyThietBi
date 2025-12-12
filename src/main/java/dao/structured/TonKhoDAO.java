package dao.structured;

import db.DBUtil;
import java.sql.*;

public class TonKhoDAO {

    // ===== 1. Tăng tồn kho =====
    public boolean increase(int idThietBi, int soLuong) {
        String sql = "UPDATE tonkho SET soLuongTon = soLuongTon + ? WHERE idThietBi = ?";

        System.out.println(">>> SQL chạy: " + sql);

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, soLuong);
            ps.setInt(2, idThietBi);

            int rows = ps.executeUpdate();
            return rows > 0;  // Có dòng bị ảnh hưởng → thành công

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== 2. Giảm tồn kho =====
    public boolean decrease(int idThietBi, int soLuong) {
        String sql = "UPDATE TonKho SET soLuongTon = soLuongTon - ? WHERE idThietBi = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, soLuong);
            ps.setInt(2, idThietBi);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== 3. Lấy số lượng tồn =====
    public int getSoLuongTon(int idThietBi) {
        String sql = "SELECT soLuongTon FROM TonKho WHERE idThietBi = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idThietBi);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("soLuongTon");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean insert(int idThietBi, int soLuongTon) {
        String sql = "INSERT INTO TonKho(idThietBi, soLuongTon) VALUES(?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idThietBi);
            pst.setInt(2, soLuongTon);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
