package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {

        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            while (true) {

                try (
                    Socket socket = server.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String fromClient = input.readUTF();
                    String toClient = "A record # 12 was sent!";
                    output.writeUTF(toClient);

                    System.out.println("Received: " + fromClient);
                    System.out.print("Sent: " + toClient);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
