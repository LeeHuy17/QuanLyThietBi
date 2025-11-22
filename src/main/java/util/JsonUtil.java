package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtil {

    // Tạo ObjectMapper (cấu hình hỗ trợ LocalDate)
    private static final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules()  // Tự nhận dạng module JSR310 cho LocalDate
            .enable(SerializationFeature.INDENT_OUTPUT); // Xuất JSON đẹp, có thụt lề

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
            T[] array = mapper.readValue(new File(filePath), clazz);
            return List.of(array);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
