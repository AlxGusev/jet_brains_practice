package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) throws IOException {

        System.out.println("Client started!");

        try (
            Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
            ) {

            String toServer = "Give me a record # 12";
            output.writeUTF(toServer);
            String fromServer = input.readUTF();

            System.out.println("Sent: " + toServer);
            System.out.print("Received: " + fromServer);
        }

    }
}
