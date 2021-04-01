package server;

import com.google.gson.Gson;
import server.service.ServerService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread{

    private final Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        ServerService service = new ServerService();

        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream()))
        {
            String req = input.readUTF();
            output.writeUTF(new Gson().toJson(service.parseString(req)));

            Server.flag = service.exit;

            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
