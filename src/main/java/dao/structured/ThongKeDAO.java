package dao.structured;

import db.DBUtil;
import model.ThietBi;
import model.LichSuTinhTrang;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    // TRẢ VỀ LIST THIẾT BỊ
    private ThietBi mapThietBi(ResultSet rs) throws SQLException {
        ThietBi tb = new ThietBi();
        tb.setIdThietBi(rs.getInt("idThietBi"));
        tb.setTenThietBi(rs.getString("tenThietBi"));
        tb.setIdLoaiThietBi(rs.getInt("idLoaiThietBi"));
        tb.setTinhTrang(rs.getString("tinhTrang"));
        tb.setMoTa(rs.getString("moTa"));

        // ===== kiểm tra cột soLuongTon có tồn tại không =====
        int soLuongTon = 0;
        try {
            soLuongTon = rs.getInt("soLuongTon");
        } catch (SQLException ignore) {}
        tb.setSoLuongTon(soLuongTon);

        return tb;
    }

    public List<ThietBi> dsDangDung() {
        String sql = """
        SELECT tb.*, tk.soLuongTon
        FROM ThietBi tb
        LEFT JOIN tonkho tk ON tb.idThietBi = tk.idThietBi
        WHERE tb.tinhTrang = 'Đang dùng'
    """;
        return getListThietBi(sql);
    }


    public List<ThietBi> dsHong() {
        String sql = """
        SELECT tb.*, tk.soLuongTon
        FROM ThietBi tb
        LEFT JOIN tonkho tk ON tb.idThietBi = tk.idThietBi
        WHERE tb.tinhTrang = 'Hỏng'
    """;
        return getListThietBi(sql);
    }

    public List<ThietBi> dsTonKho() {
        String sql = """
            SELECT tb.*, tk.soLuongTon
            FROM ThietBi tb
            JOIN tonkho tk ON tb.idThietBi = tk.idThietBi 
             WHERE tk.soLuongTon > 0
        """;
        return getListThietBi(sql);
    }

    private List<ThietBi> getListThietBi(String sql) {
        List<ThietBi> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapThietBi(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
