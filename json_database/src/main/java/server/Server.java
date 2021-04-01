package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Server {
    private final String ADDRESS = "127.0.0.1";
    private final int PORT = 23456;
    public static boolean flag = false;

    public void startServer() {

        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {

            while(!flag) {
                Session session = new Session(server.accept());
                session.start();
                session.join();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
