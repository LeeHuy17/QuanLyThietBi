package org.example;

import db.DBUtil;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();

        if (conn != null) {
            System.out.println("Kết nối thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
