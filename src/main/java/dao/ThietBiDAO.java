package dao;

import db.DBUtil;
import model.ThietBi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThietBiDAO {

    // Lấy danh sách thiết bị
    public List<ThietBi> getAll() {
        List<ThietBi> list = new ArrayList<>();

        String sql = "SELECT * FROM ThietBi";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ThietBi tb = new ThietBi();
                tb.setIdThietBi(rs.getInt("idThietBi"));
                tb.setTenThietBi(rs.getString("tenThietBi"));
                tb.setIdLoaiThietBi(rs.getInt("idLoaiThietBi"));
                tb.setTinhTrang(rs.getString("tinhTrang"));
                tb.setMoTa(rs.getString("moTa"));

                list.add(tb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // Thêm thiết bị
    public int insertReturnId(ThietBi tb) {
        String sql = "INSERT INTO ThietBi(tenThietBi, idLoaiThietBi, tinhTrang, moTa) VALUES(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tb.getTenThietBi());
            stmt.setInt(2, tb.getIdLoaiThietBi());
            stmt.setString(3, tb.getTinhTrang());
            stmt.setString(4, tb.getMoTa());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return -1; // Thêm thất bại
            }

            // Lấy ID vừa thêm
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1; // Không có ID trả về
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Cập nhật thiết bị
    public boolean update(ThietBi tb) {
        String sql = "UPDATE ThietBi SET tenThietBi=?, idLoaiThietBi=?, tinhTrang=?, moTa=? WHERE idThietBi=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tb.getTenThietBi());
            stmt.setInt(2, tb.getIdLoaiThietBi());
            stmt.setString(3, tb.getTinhTrang());
            stmt.setString(4, tb.getMoTa());
            stmt.setInt(5, tb.getIdThietBi());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa thiết bị
    public boolean delete(int id) {
        String sql = "DELETE FROM ThietBi WHERE idThietBi=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}

