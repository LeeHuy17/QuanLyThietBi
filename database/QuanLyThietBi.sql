CREATE DATABASE IF NOT EXISTS QuanLyThietBi;
USE QuanLyThietBi;

CREATE TABLE ThietBi (
    idThietBi INT AUTO_INCREMENT PRIMARY KEY,
    tenThietBi VARCHAR(100) NOT NULL,
    loaiThietBi VARCHAR(50),
    tinhTrang ENUM('Hỏng', 'Đang dùng') DEFAULT 'Đang dùng',
    moTa TEXT
);

CREATE TABLE NhapKho (
    idNhap INT AUTO_INCREMENT PRIMARY KEY,
    idThietBi INT NOT NULL,
    soLuong INT NOT NULL,
    ngayNhap DATE NOT NULL,
    nguoiNhap VARCHAR(50),
    FOREIGN KEY (idThietBi) REFERENCES ThietBi(idThietBi)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE XuatKho (
    idXuat INT AUTO_INCREMENT PRIMARY KEY,
    idThietBi INT NOT NULL,
    soLuong INT NOT NULL,
    ngayXuat DATE NOT NULL,
    nguoiXuat VARCHAR(50),
    FOREIGN KEY (idThietBi) REFERENCES ThietBi(idThietBi)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


CREATE TABLE TonKho (
    idThietBi INT PRIMARY KEY,
    soLuongTon INT NOT NULL DEFAULT 0,
    tinhTrang ENUM('Hỏng', 'Đang dùng') DEFAULT 'Đang dùng',
    FOREIGN KEY (idThietBi) REFERENCES ThietBi(idThietBi)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE LichSuTinhTrang (
    idLichSu INT AUTO_INCREMENT PRIMARY KEY,
    idThietBi INT NOT NULL,
    tinhTrangCu ENUM('Hỏng', 'Đang dùng') NOT NULL,
    tinhTrangMoi ENUM('Hỏng', 'Đang dùng') NOT NULL,
    ngayCapNhat DATE NOT NULL,
    ghiChu TEXT,
    FOREIGN KEY (idThietBi) REFERENCES ThietBi(idThietBi)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE LoaiThietBi (
    idLoaiThietBi INT AUTO_INCREMENT PRIMARY KEY,
    tenLoai VARCHAR(50) NOT NULL,
    moTa TEXT
);

ALTER TABLE ThietBi
ADD COLUMN idLoaiThietBi INT;

ALTER TABLE ThietBi
ADD CONSTRAINT fk_ThietBi_LoaiThietBi
FOREIGN KEY (idLoaiThietBi)
REFERENCES LoaiThietBi(idLoaiThietBi)
ON DELETE SET NULL
ON UPDATE CASCADE;

ALTER TABLE ThietBi DROP COLUMN loaiThietBi;





