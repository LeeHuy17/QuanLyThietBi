package view;

import client.ClientConnection;
import commons.Action;
import commons.Message;
import commons.Response;
import model.NhapKho;
import model.ThietBi;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class FormNhapKho extends JFrame {

    private JComboBox<ThietBi> cboThietBi;
    private JTextField txtSoLuong;

    public FormNhapKho() {
        setTitle("Nhập Kho Thiết Bị");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));

        cboThietBi = new JComboBox<>();
        txtSoLuong = new JTextField();

        form.add(new JLabel("Thiết bị:"));
        form.add(cboThietBi);
        form.add(new JLabel("Số lượng nhập:"));
        form.add(txtSoLuong);

        JButton btnNhap = new JButton("Nhập kho");
        form.add(new JLabel(""));
        form.add(btnNhap);

        add(form, BorderLayout.CENTER);

        // Sự kiện
        btnNhap.addActionListener(e -> nhapKho());

        // Tải danh sách thiết bị từ Server
        loadThietBi();
    }

    private void nhapKho() {
        try {
            ThietBi tb = (ThietBi) cboThietBi.getSelectedItem();
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            if (tb == null) {
                JOptionPane.showMessageDialog(this, "Chưa chọn thiết bị");
                return;
            }

            NhapKho nk = new NhapKho();
            nk.setIdThietBi(tb.getIdThietBi());
            nk.setSoLuong(soLuong);
            nk.setNgayNhap(new Date(System.currentTimeMillis()).toLocalDate());

            // Người nhập mặc định
            nk.setNguoiNhap("Hệ thống");

            System.out.println("ID gửi lên server = " + tb.getIdThietBi());

            Message msg = new Message(Action.NHAP_KHO, nk);

            Response res = ClientConnection.getInstance().send(msg);

            JOptionPane.showMessageDialog(this, res.getMessage());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi nhập kho: " + ex.getMessage());
        }
    }


    private void loadThietBi() {
        Message msg = new Message(Action.GET_ALL_THIET_BI, null);
        Response res = ClientConnection.getInstance().send(msg);

        if (res.isSuccess()) {
            List<ThietBi> list = (List<ThietBi>) res.getData();
            for (ThietBi tb : list) {
                cboThietBi.addItem(tb);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tải được danh sách thiết bị");
        }
    }


}
