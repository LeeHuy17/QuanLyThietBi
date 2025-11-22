package dao;

import db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThongKeDAO {

    // Tổng số thiết bị
    public int tongThietBi() {
        String sql = "SELECT COUNT(*) FROM ThietBi";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Số thiết bị đang dùng
    public int soDangDung() {
        String sql = "SELECT COUNT(*) FROM ThietBi WHERE tinhTrang = 'Đang dùng'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // Số thiết bị hỏng
    public int soHong() {
        String sql = "SELECT COUNT(*) FROM ThietBi WHERE tinhTrang = 'Hỏng'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // Số lượng tồn kho theo thiết bị
    public int tonKho(int idThietBi) {
        String sql = "SELECT soLuongTon FROM TonKho WHERE idThietBi = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idThietBi);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("soLuongTon");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
