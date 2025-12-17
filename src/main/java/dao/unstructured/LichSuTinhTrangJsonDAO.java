package dao.unstructured;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.LichSuTinhTrang;
import util.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LichSuTinhTrangJsonDAO {

    private static final String FILE_PATH = "src/main/resources/data/json/lich_su_tinh_trang.json";

    // Đọc toàn bộ lịch sử từ file JSON
    public List<LichSuTinhTrang> getAll() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            LichSuTinhTrang[] arr = mapper.readValue(file, LichSuTinhTrang[].class);

            // chuyển sang ArrayList để có thể add()
            return new ArrayList<>(Arrays.asList(arr));

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    // Thêm bản ghi mới
    public void insert(LichSuTinhTrang item) {
        List<LichSuTinhTrang> list = getAll();
        list.add(item);
        JsonUtil.writeListToFile(list, FILE_PATH);
    }

    // Ghi đè toàn bộ danh sách
    public void saveAll(List<LichSuTinhTrang> list) {
        JsonUtil.writeListToFile(list, FILE_PATH);
    }
}