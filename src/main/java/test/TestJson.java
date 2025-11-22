package test;

import model.LichSuTinhTrang;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class TestJson {
    public static void main(String[] args) {
        List<LichSuTinhTrang> ds = new ArrayList<>();
        ds.add(new LichSuTinhTrang(
                "TB01",
                "Máy chạy bộ",
                "2025-11-21",
                "Kêu to khi chạy",
                "Hỏng"
        ));

        // Ghi vào file json
        JsonUtil.writeListToFile(ds, "lich_su_thiet_bi.json");
        System.out.println("Ghi JSON thành công!");

        // Đọc từ file json
        List<LichSuTinhTrang> dsRead =
                JsonUtil.readListFromFile("lich_su_thiet_bi.json", LichSuTinhTrang[].class);

        System.out.println("Đọc JSON:");
        dsRead.forEach(item ->
                System.out.println(item.getMaThietBi() + " | " + item.getTinhTrang())
        );
    }
}
