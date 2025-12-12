package server;

import commons.Message;
import commons.Response;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {

                Message req = (Message) in.readObject();
                Response res = RequestProcessor.process(req);

                out.writeObject(res);
                out.flush();
            }
        } catch (Exception e) {
            System.out.println("Client đã ngắt kết nối.");
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}
