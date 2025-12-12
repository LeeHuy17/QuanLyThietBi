package controller;

import dao.structured.ThongKeDAO;
import model.ThietBi;

import java.util.List;

public class ControllerThongKe {

    private ThongKeDAO tkDAO = new ThongKeDAO();

    // Thống kê ds thiết bị theo tình trạng

    public List<ThietBi> danhSachDangDung() {
        return tkDAO.dsDangDung();
    }

    public List<ThietBi> danhSachHong() {
        return tkDAO.dsHong();
    }

    public List<ThietBi> danhSachTonKho() {
        return tkDAO.dsTonKho();
    }
}
