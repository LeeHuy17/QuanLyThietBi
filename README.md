# Dự án Quản lý Thiết Bị

## 1. Yêu cầu
- Java 17 hoặc cao hơn
- MySQL 8 hoặc cao hơn
- MySQL JDBC Driver (Connector/J)
- IntelliJ IDEA hoặc IDE Java khác
- Thư viện JSON: Jackson (đã tích hợp trong Maven)
- Maven để quản lý dependencies

## 2. Thư mục và cấu trúc dự án
src/
├─ main/
│ ├─ java/
│ │ ├─ db/ # Kết nối database (DBUtil.java)
│ │ ├─ model/ # Các class đại diện bảng DB (ThietBi, NhapKho, XuatKho, LichSuTinhTrang)
│ │ ├─ dao/ # Data Access Objects (ThietBiDAO, NhapKhoDAO, XuatKhoDAO, ThongKeDAO)
│ │ ├─ service/ # (Nếu cần xử lý logic nghiệp vụ phức tạp)
│ │ ├─ controller/ # Controller (thuộc phần của thành viên B)
│ │ └─ view/ # Giao diện (Swing/Console)
│ └─ resources/ # File cấu hình, ví dụ config.properties
└─ test/ # File test DAO (TestDAO.java)


## 3. Tạo cơ sở dữ liệu
Mở MySQL Workbench hoặc terminal, sau đó chạy file SQL script `database/QuanLyThietBi.sql` để tạo database và các bảng:
```bash
mysql -u root -p < database/QuanLyThietBi.sql

Kiểm tra database và các bảng đã được tạo:
USE QuanLyThietBi;
SHOW TABLES;


## 4. Cấu hình kết nối cơ sở dữ liệu
Mở file src/main/resources/config.properties và cập nhật thông tin MySQL:
private static final String URL = "jdbc:mysql://localhost:3306/QuanLyThietBi";
private static final String USER = "root";
private static final String PASS = "password"; // đổi thành mật khẩu MySQL của bạn


## 5. Thư viện sử dụng
Trong pom.xml (Maven), đã khai báo:
<dependencies>
    <!-- MySQL Connector/J -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>

    <!-- Jackson JSON -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
</dependencies>