package view;

import client.ClientConnection;
import commons.Action;
import commons.Message;
import commons.Response;
import model.ThietBi;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormThietBi extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FormThietBi() {
        setTitle("Quản Lý Thiết Bị");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{
                "ID", "Tên TB", "Loại", "Tình trạng", "Mô tả"
        }, 0);

        table = new JTable(model);

        // FORM INPUT
        JPanel topPanel = new JPanel(new FlowLayout());

        JTextField txtTen = new JTextField(10);
        JTextField txtLoai = new JTextField(5);

        JComboBox<String> cbTinhTrang = new JComboBox<>();
        cbTinhTrang.addItem("Hỏng");
        cbTinhTrang.addItem("Đang dùng");

        JTextField txtMoTa = new JTextField(10);

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnSearch = new JButton("Tìm kiếm");

        JTextField txtKeyword = new JTextField(14);

        // ====== Sắp xếp đúng thứ tự ======
        topPanel.add(new JLabel("Tên:"));
        topPanel.add(txtTen);

        topPanel.add(new JLabel("Loại:"));
        topPanel.add(txtLoai);

        topPanel.add(new JLabel("Tình trạng:"));
        topPanel.add(cbTinhTrang);

        topPanel.add(new JLabel("Mô tả:"));
        topPanel.add(txtMoTa);

        // ---- Tìm kiếm ----
        topPanel.add(new JLabel("Từ khóa:"));
        topPanel.add(txtKeyword);
        topPanel.add(btnSearch);

        // ---- Các nút chức năng ----
        topPanel.add(btnAdd);
        topPanel.add(btnUpdate);
        topPanel.add(btnDelete);


        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // nút thêm
        btnAdd.addActionListener(e -> {
            try {
                int loai = Integer.parseInt(txtLoai.getText());

                ThietBi tb = new ThietBi(
                        txtTen.getText(),
                        loai,
                        cbTinhTrang.getSelectedItem().toString(),
                        txtMoTa.getText()
                );

                Response res = ClientConnection.getInstance()
                        .send(new Message(Action.INSERT_THIET_BI, tb));

                JOptionPane.showMessageDialog(this, res.getMessage());
                loadData();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Loại thiết bị phải là số!");
            }
        });
        // nuts xóa
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn dòng cần xoá");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            Response res = ClientConnection.getInstance()
                    .send(new Message(Action.DELETE_THIET_BI, id));

            JOptionPane.showMessageDialog(this, res.getMessage());
            loadData();
        });

        // nut sửa
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            // 1) Lấy thông tin cũ để ghi JSON (TRƯỚC KHI CẬP NHẬT SQL)
            String tinhTrangCu = model.getValueAt(row, 3).toString();

            // 2) Lấy thông tin mới từ form
            String ten = txtTen.getText().trim();
            int idLoai = Integer.parseInt(txtLoai.getText().trim());
            String tinhTrangMoi = cbTinhTrang.getSelectedItem().toString();
            String moTa = txtMoTa.getText().trim();

            // 3) Ghi lịch sử JSON TRƯỚC (bắt buộc)
            Message msgJson = new Message(
                    Action.UPDATE_TINH_TRANG,
                    id,
                    tinhTrangCu,
                    tinhTrangMoi,
                    moTa
            );

            Response resJson = ClientConnection.getInstance().send(msgJson);
            if (!resJson.isSuccess()) {
                JOptionPane.showMessageDialog(this, resJson.getMessage());
                return;
            }

            // 4) Sau đó mới cập nhật SQL
            ThietBi tb = new ThietBi(id, ten, idLoai, tinhTrangMoi, moTa);
            Message msgUpdateTB = new Message(Action.UPDATE_THIET_BI, tb);

            Response res1 = ClientConnection.getInstance().send(msgUpdateTB);
            JOptionPane.showMessageDialog(this, res1.getMessage());

            loadData();
        });
        // Nút tìm kiếm
        btnSearch.addActionListener(e -> {
            // DocumentListener theo dõi khi xóa từ khóa
            txtKeyword.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) { checkEmpty(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { checkEmpty(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { checkEmpty(); }

                private void checkEmpty() {
                    String key = txtKeyword.getText().trim();
                    if (key.isEmpty()) {
                        loadData();     // load lại toàn bộ danh sách
                    }
                }
            });

            String keyword = txtKeyword.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nhập từ khóa tìm kiếm");
                return;
            }
            keyword = keyword.toLowerCase();

            Message msg = new Message(Action.SEARCH_THIET_BI, keyword);
            Response res = ClientConnection.getInstance().send(msg);

            if (res == null || !res.isSuccess()) {
                String m = res == null ? "Không nhận được phản hồi từ server" : res.getMessage();
                JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + m);
                // giữ nguyên bảng
                return;
            }

            List<ThietBi> list = (List<ThietBi>) res.getData();
            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả.");
                // hiển thị lại danh sách đầy đủ, gọi loadData():
                 loadData();
                return;
            }

            // nếu có kết quả, hiển thị
            loadData(list);
        });

        // Chọn dòng để đổ form
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtTen.setText(model.getValueAt(row, 1).toString());
                txtLoai.setText(model.getValueAt(row, 2).toString());
                cbTinhTrang.setSelectedItem(model.getValueAt(row, 3).toString());
                txtMoTa.setText(model.getValueAt(row, 4).toString());
            }
        });

        loadData();
    }

    // LOAD DỮ LIỆU TỪ SERVER

    // lưu snapshot dữ liệu hiện tại (nếu muốn khôi phục)
    final java.util.List<ThietBi> lastFullList = new java.util.ArrayList<>();
    private void loadData() {
        model.setRowCount(0);

        Response res = ClientConnection.getInstance()
                .send(new Message(Action.GET_ALL_THIET_BI, null));

        if (!res.isSuccess()) {
            JOptionPane.showMessageDialog(this, res.getMessage());
            return;
        }

        List<ThietBi> list = (List<ThietBi>) res.getData();

        if (list == null) list = java.util.Collections.emptyList();
        lastFullList.clear();
        lastFullList.addAll(list);

        for (ThietBi tb : list) {
            model.addRow(new Object[]{
                    tb.getIdThietBi(),
                    tb.getTenThietBi(),
                    tb.getIdLoaiThietBi(),
                    tb.getTinhTrang(),
                    tb.getMoTa()
            });
        }
    }
    // loadData cho phần tìm kiếm
    private void loadData(List<ThietBi> list) {
        model.setRowCount(0);
        if (list == null || list.isEmpty()) return;
        for (ThietBi tb : list) {
            model.addRow(new Object[]{
                    tb.getIdThietBi(),
                    tb.getTenThietBi(),
                    tb.getIdLoaiThietBi(),
                    tb.getTinhTrang(),
                    tb.getMoTa()
            });
        }
    }
}
