package client;

import commons.Message;
import commons.Response;

import java.io.*;
import java.net.Socket;

public class ClientConnection {

    private static ClientConnection instance;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private ClientConnection() {
        try {
            socket = new Socket("127.0.0.1", 8888);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ClientConnection getInstance() {
        if (instance == null) instance = new ClientConnection();
        return instance;
    }

    public Response send(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();

            return (Response) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, "Lỗi kết nối tới server", null);
        }
    }
}
