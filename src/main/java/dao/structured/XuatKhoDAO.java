package dao.structured;

import db.DBUtil;
import model.XuatKho;

import java.sql.*;

public class XuatKhoDAO {

    public boolean insert(XuatKho xk) {
        String sql = "INSERT INTO XuatKho(idThietBi, soLuong, ngayXuat, nguoiXuat) VALUES(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, xk.getIdThietBi());
            stmt.setInt(2, xk.getSoLuong());
            stmt.setDate(3, Date.valueOf(xk.getNgayXuat()));
            stmt.setString(4, xk.getNguoiXuat());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
