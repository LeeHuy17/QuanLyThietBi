package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtil {

    // ObjectMapper hỗ trợ LocalDate và không ghi dạng timestamp
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())                  // Hỗ trợ LocalDate
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)  // Không ghi dạng "timestamp" hoặc array
            .enable(SerializationFeature.INDENT_OUTPUT);

    // Ghi danh sách đối tượng ra file JSON
    public static <T> void writeListToFile(List<T> list, String filePath) {
        try {
            mapper.writeValue(new File(filePath), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Đọc danh sách đối tượng từ file JSON
    public static <T> List<T> readListFromFile(String filePath, Class<T[]> clazz) {
        try {
            File file = new File(filePath);
            if (!file.exists()) return new ArrayList<>();

            T[] array = mapper.readValue(file, clazz);
            return new ArrayList<>(Arrays.asList(array)); // TRẢ VỀ LIST CÓ THỂ THÊM/SỬA/XÓA
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
