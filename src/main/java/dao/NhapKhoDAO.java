package dao;

import db.DBUtil;
import model.NhapKho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhapKhoDAO {

    public boolean insert(NhapKho nk) {
        String sql = "INSERT INTO NhapKho(idThietBi, soLuong, ngayNhap, nguoiNhap) VALUES(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nk.getIdThietBi());
            stmt.setInt(2, nk.getSoLuong());
            stmt.setDate(3, Date.valueOf(nk.getNgayNhap()));
            stmt.setString(4, nk.getNguoiNhap());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
