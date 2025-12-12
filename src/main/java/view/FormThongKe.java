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

public class FormThongKe extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FormThongKe() {
        setTitle("Thống Kê Thiết Bị");
        setSize(700, 450);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{
                "ID", "Tên thiết bị", "Loại", "Tình trạng", "Mô tả", "Số lượng tồn"
        }, 0);

        table = new JTable(model);

        JPanel top = new JPanel(new FlowLayout());

        JButton btnTonKho = new JButton("Tồn Kho");
        JButton btnHong = new JButton("Thiết Bị Hỏng");
        JButton btnDangSuDung = new JButton("Đang Sử Dụng");

        top.add(btnTonKho);
        top.add(btnHong);
        top.add(btnDangSuDung);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnTonKho.addActionListener(e -> load(Action.GET_TON_KHO));
        btnHong.addActionListener(e -> load(Action.GET_THIET_BI_HONG));
        btnDangSuDung.addActionListener(e -> load(Action.GET_DANG_SU_DUNG));
    }

    private void load(Action action) {
        Response res = ClientConnection.getInstance().send(new Message(action, null));

        if (!res.isSuccess()) {
            JOptionPane.showMessageDialog(this, res.getMessage());
            return;
        }

        Object data = res.getData();
        if (data == null || !(data instanceof List)) {
            JOptionPane.showMessageDialog(this, "Dữ liệu trả về không hợp lệ!");
            return;
        }

        List<ThietBi> ds = (List<ThietBi>) data;
        reloadTable(ds);
    }

    private void reloadTable(List<ThietBi> list) {
        model.setRowCount(0);

        for (ThietBi tb : list) {
            model.addRow(new Object[]{
                    tb.getIdThietBi(),
                    tb.getTenThietBi(),
                    tb.getIdLoaiThietBi(),
                    tb.getTinhTrang(),
                    tb.getMoTa(),
                    tb.getSoLuongTon()
            });
        }
    }
}
