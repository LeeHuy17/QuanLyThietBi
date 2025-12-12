package view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Quản Lý Thiết Bị - Main Menu");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnTB = new JButton("Quản lý thiết bị");
        JButton btnNhap = new JButton("Nhập kho");
        JButton btnXuat = new JButton("Xuất kho");
        JButton btnThongKe = new JButton("Thống kê");

        btnTB.addActionListener(e -> new FormThietBi().setVisible(true));
        btnNhap.addActionListener(e -> new FormNhapKho().setVisible(true));
        btnXuat.addActionListener(e -> new FormXuatKho().setVisible(true));
        btnThongKe.addActionListener(e -> new FormThongKe().setVisible(true));

        setLayout(new GridLayout(4, 1, 10, 10));

        add(btnTB);
        add(btnNhap);
        add(btnXuat);
        add(btnThongKe);
    }
}
