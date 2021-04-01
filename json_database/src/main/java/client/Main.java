package client;

import client.service.ClientService;
import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args){

        ClientService service = new ClientService();
        Request request = new Request();

        JCommander.newBuilder()
                .addObject(request)
                .build()
                .parse(args);

        System.out.println("Client started!");

        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            String req = service.parseRequest(request);

            System.out.println("Sent: " + req);

            output.writeUTF(req);

            String response = input.readUTF();

            System.out.println("Received: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
