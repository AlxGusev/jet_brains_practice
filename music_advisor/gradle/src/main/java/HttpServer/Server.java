package HttpServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Server {

    private final String client_id = "8a8a4451652340328e2d3747de1138eb";
    private final String client_secret = "feb2fef3ba25447aa8dab63ae863c4b8";
    private final String redirect_uri = "http://localhost";
    private final String serverPoint;
    HttpServer httpServer;
    private final int port = 8080;
    private String code;
    public boolean gotTheCode = false;
    private String responseJson;

    public Server(String serverPoint) {
        this.serverPoint = serverPoint;
    }

    public void startServer() throws IOException {

        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.start();
        httpServer.createContext("/",
                exchange -> {

                    String response;
                    code = exchange.getRequestURI().getQuery();

                    if (code != null && code.contains("code")) {
                        response = "Got the code. Return back to your program.";
                    } else {
                        response = "Authorization code not found. Try again.";
                    }
                    gotTheCode = true;
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.getResponseBody().close();
                });

        System.out.println("use this link to request the access code:");
        System.out.println(serverPoint + "/authorize?" +
                "client_id=" + client_id +
                "&redirect_uri=" + redirect_uri + ":" + port +
                "&response_type=code");
        System.out.println("waiting for code...");
    }

    public void readClientRequest () {

        System.out.println("code received");
        System.out.println("making http request for access_token...");

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(serverPoint + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "client_id=" + client_id + "&" +
                                "client_secret=" + client_secret + "&" +
                                "grant_type=authorization_code&" +
                                code + "&" +
                                "redirect_uri=" + redirect_uri + ":" + port))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            responseJson = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        httpServer.stop(1);
    }

    public String getResponseJson() {
        return responseJson;
    }

    public String getCode() {
        return code;
    }


}
