package dao.structured;

import db.DBUtil;
import model.ThietBi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThietBiDAO {

    // Lấy tất cả thiết bị
    public List<ThietBi> getAll() {
        List<ThietBi> list = new ArrayList<>();
        String sql = "SELECT * FROM ThietBi";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ThietBi tb = new ThietBi(
                        rs.getInt("idThietBi"),
                        rs.getString("tenThietBi"),
                        rs.getInt("idLoaiThietBi"),
                        rs.getString("tinhTrang"),
                        rs.getString("moTa")
                );
                list.add(tb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm thiết bị và tạo tồn kho (trả về id mới)
    public int insertReturnId(ThietBi tb) {
        int id = -1;

        String sqlInsertTB =
                "INSERT INTO ThietBi(tenThietBi, idLoaiThietBi, tinhTrang, moTa) VALUES (?, ?, ?, ?)";

        String sqlInsertTonKho =
                "INSERT INTO TonKho(idThietBi, soLuongTon) VALUES (?, 0)";

        try (Connection conn = DBUtil.getConnection()) {

            conn.setAutoCommit(false);  // bắt đầu transaction

            try (PreparedStatement ps = conn.prepareStatement(sqlInsertTB, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, tb.getTenThietBi());
                ps.setInt(2, tb.getIdLoaiThietBi());
                ps.setString(3, tb.getTinhTrang());
                ps.setString(4, tb.getMoTa());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return -1;
                    }
                    id = rs.getInt(1);
                }
            }

            // tạo dòng tonkho cho id vừa sinh
            try (PreparedStatement psTon = conn.prepareStatement(sqlInsertTonKho)) {
                psTon.setInt(1, id);
                psTon.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            // nếu cần, có thể rollback nhưng try-with-resources đã đóng conn; ở đây chỉ log
        }

        return id;
    }

    public int insertAndGetId(ThietBi tb) {
        String sql = "INSERT INTO ThietBi(tenThietBi, idLoaiThietBi, tinhTrang, moTa) VALUES(?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, tb.getTenThietBi());
            pst.setInt(2, tb.getIdLoaiThietBi());
            pst.setString(3, tb.getTinhTrang());
            pst.setString(4, tb.getMoTa());

            int affected = pst.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // idThietBi vừa tạo
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
            return false;
        }
    }

    // Cập nhật tình trạng
    public boolean updateTinhTrang(int idThietBi, String tinhTrang) {
        String sql = "UPDATE ThietBi SET tinhTrang=? moTa=? WHERE idThietBi=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tinhTrang);
            stmt.setInt(2, idThietBi);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
            return false;
        }
    }

    // Lấy theo ID
    public ThietBi findById(int id) {
        String sql = "SELECT * FROM ThietBi WHERE idThietBi=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ThietBi(
                            rs.getInt("idThietBi"),
                            rs.getString("tenThietBi"),
                            rs.getInt("idLoaiThietBi"),
                            rs.getString("tinhTrang"),
                            rs.getString("moTa")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Kiểm tra tên thiết bị
    public boolean existsByNameAndLoai(String ten, int idLoai) {
        String sql = "SELECT 1 FROM ThietBi WHERE tenThietBi = ? AND idLoaiThietBi = ? LIMIT 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ten);
            ps.setInt(2, idLoai);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // hàm tìm kiếm
    public List<ThietBi> search(String keyword) {
        List<ThietBi> list = new ArrayList<>();
        String sql = "SELECT * FROM ThietBi WHERE tenThietBi LIKE ? OR moTa LIKE ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThietBi tb = new ThietBi();
                tb.setIdThietBi(rs.getInt("idThietBi"));
                tb.setTenThietBi(rs.getString("tenThietBi"));
                tb.setIdLoaiThietBi(rs.getInt("idLoaiThietBi"));
                tb.setTinhTrang(rs.getString("tinhTrang"));
                tb.setMoTa(rs.getString("moTa"));
                list.add(tb);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
