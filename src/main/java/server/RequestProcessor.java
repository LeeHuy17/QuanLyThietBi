package server;

import commons.Action;
import commons.Message;
import commons.Response;

import controller.ControllerThietBi;
import controller.ControllerNhap;
import controller.ControllerXuat;
import controller.ControllerThongKe;

import model.ThietBi;
import model.NhapKho;
import model.XuatKho;

import java.util.List;

import static java.lang.System.out;

public class RequestProcessor {

    private static ControllerThietBi ctrlTB = new ControllerThietBi();
    private static ControllerNhap ctrlNhap = new ControllerNhap();
    private static ControllerXuat ctrlXuat = new ControllerXuat();
    private static ControllerThongKe ctrlTK = new ControllerThongKe();

    public static Response process(Message msg) {

        try {
            switch (msg.getAction()) {

                case GET_ALL_THIET_BI:
                    return new Response(true, "Danh sách thiết bị", ctrlTB.getAll());

                case INSERT_THIET_BI: {
                    ThietBi tb = (ThietBi) msg.getData();
                    String result = ctrlTB.addThietBi(tb);
                    return new Response(result.equals("SUCCESS"), result, null);
                }

                case UPDATE_THIET_BI: {
                    ThietBi tb = (ThietBi) msg.getData();
                    String result = ctrlTB.updateThietBi(tb);
                    return new Response(result.equals("SUCCESS"), result, null);
                }

                case DELETE_THIET_BI: {
                    int id = (int) msg.getData();
                    String result = ctrlTB.deleteThietBi(id);
                    return new Response(result.equals("SUCCESS"), result, null);
                }

                case NHAP_KHO: {
                    NhapKho nk = (NhapKho) msg.getData();

                    String result = ctrlNhap.nhapKho(nk);
                    boolean ok = result.equals("SUCCESS");

                    return new Response(ok, result, null);
                }

                case UPDATE_TINH_TRANG: {
                    Integer id = msg.getThietBiId();
                    String cu = msg.getTinhTrangCu();
                    String moi = msg.getTinhTrangMoi();
                    String ghiChu = msg.getGhiChu();

                    String result = ctrlTB.capNhatTinhTrang(id, moi, ghiChu);

                    boolean ok = result.equals("SUCCESS");

                    return new Response(ok, result, null);
                }

                case SEARCH_THIET_BI: {
                    String keyword = (String) msg.getData();
                    keyword = keyword.trim().toLowerCase();
                    List<ThietBi> list = ctrlTB.search(keyword);
                    return new Response(true, "OK", list);
                }

                case XUAT_KHO: {
                    XuatKho xk = (XuatKho) msg.getData();

                    boolean ok = ctrlXuat.xuatKho(xk);

                    return new Response(ok, ok ? "SUCCESS" : "Lỗi xuất kho", null);
                }

                case GET_TON_KHO:
                    return new Response(true, "OK", ctrlTK.danhSachTonKho());

                case GET_THIET_BI_HONG:
                    return new Response(true, "OK", ctrlTK.danhSachHong());

                case GET_DANG_SU_DUNG:
                    return new Response(true, "OK", ctrlTK.danhSachDangDung());

                default:
                    return new Response(false, "Action không hợp lệ", null);
            }

        } catch (Exception e) {
            return new Response(false, "Lỗi xử lý: " + e.getMessage(), null);
        }
    }
}
