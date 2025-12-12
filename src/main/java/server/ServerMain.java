package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) {
        int port = 8888;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server đang chạy tại cổng " + port);

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client kết nối: " + client.getInetAddress());

                // mỗi client là một thread
                new ClientHandler(client).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
