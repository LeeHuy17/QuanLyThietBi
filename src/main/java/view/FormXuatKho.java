package view;

import client.ClientConnection;
import commons.Action;
import commons.Message;
import commons.Response;
import model.ThietBi;
import model.XuatKho;
import java.time.LocalDate;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormXuatKho extends JFrame {

    private JComboBox<ThietBi> cboThietBi;
    private JTextField txtSoLuong;

    public FormXuatKho() {
        setTitle("Xuất Kho Thiết Bị");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));

        cboThietBi = new JComboBox<>();
        txtSoLuong = new JTextField();

        form.add(new JLabel("Thiết bị:"));
        form.add(cboThietBi);
        form.add(new JLabel("Số lượng xuất:"));
        form.add(txtSoLuong);

        JButton btnXuat = new JButton("Xuất kho");
        form.add(new JLabel(""));
        form.add(btnXuat);

        add(form, BorderLayout.CENTER);

        btnXuat.addActionListener(e -> xuatKho());

        // Tải danh sách thiết bị từ server
        loadThietBi();
    }

    private void loadThietBi() {
        Response res = ClientConnection.getInstance()
                .send(new Message(Action.GET_ALL_THIET_BI, null));

        if (!res.isSuccess()) {
            JOptionPane.showMessageDialog(this, res.getMessage());
            return;
        }

        List<ThietBi> ds = (List<ThietBi>) res.getData();
        for (ThietBi tb : ds) {
            cboThietBi.addItem(tb);
        }
    }

    private void xuatKho() {
        ThietBi tb = (ThietBi) cboThietBi.getSelectedItem();
        int soLuong;

        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) throw new Exception();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số > 0");
            return;
        }

        // Tạo object XuatKho
        XuatKho xk = new XuatKho();
        xk.setIdThietBi(tb.getIdThietBi());
        xk.setSoLuong(soLuong);
        xk.setNgayXuat(LocalDate.now());
        xk.setNguoiXuat("Admin"); // hoặc lấy từ user đăng nhập

        // Gửi trực tiếp đối tượng XuatKho
        Response res = ClientConnection.getInstance()
                .send(new Message(Action.XUAT_KHO, xk));

        JOptionPane.showMessageDialog(this, res.getMessage());
    }

}
