package client;

import view.MainMenu;

public class ClientMain {
    public static void main(String[] args) {
        ClientConnection.getInstance(); // tạo kết nối
        new MainMenu().setVisible(true);
    }
}

