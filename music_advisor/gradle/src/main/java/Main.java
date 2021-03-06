import HttpServer.Server;
import JsonParser.CategoriesJsonParser;
import JsonParser.FeaturedJsonParser;
import JsonParser.NewAlbumsJsonParser;
import JsonParser.TokenJsonParser;
import SpotifyRequest.SpotifyRequest;
import View.Client;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static final Scanner SCANNER = new Scanner(System.in);
    private String serverPoint = "https://accounts.spotify.com";
    private String resource = "https://api.spotify.com";
    private int page = 5;
    private final String NEW_API_PATH = "/v1/browse/new-releases";
    private final String FEATURED_API_PATH = "/v1/browse/featured-playlists";
    private final String CATEGORIES_API_PATH = "/v1/browse/categories";
    private boolean access = false;
    private String clientToken;

    public static void main(String[] args) {
        Main controller = new Main();
        controller.parseArguments(args);
        controller.authorization();
        controller.menu();
    }

    private void parseArguments(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            if ("-access".equals(arguments[i])) {
                serverPoint = arguments[i + 1];
            }
            if ("-resource".equals(arguments[i])) {
                resource = arguments[i + 1];
            }
            if ("-page".equals(arguments[i])) {
                page = Integer.parseInt(arguments[i + 1]);
            }
        }
    }

    private void authorization() {
        while (!access) {
            String line = SCANNER.nextLine();
            switch (line) {
                case "auth":
                    launchServer();
                    break;
                case "exit":
                    exit();
                    break;
                default:
                    System.out.println("Please, provide access for application.");
                    break;
            }
        }
    }

    private void launchServer() {
        Server server = new Server(serverPoint);
        try {
            server.startServer();
            while (!server.gotTheCode) {
                Thread.sleep(500);
            }
            server.readClientRequest();
            if (server.getCode().contains("code")) {
                System.out.println("Success!");
                TokenJsonParser tjp = new TokenJsonParser(server.getResponseJson());
                clientToken = tjp.getToken();
                access = true;
            } else {
                System.out.println("Error!");
            }
            server.stopServer();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void menu() {

        Client client = Client.getInstance();

        while (true) {

            String[] input = SCANNER.nextLine().split(" ", 2);
            String pick = input[0];
            String mode = "";

            if (input.length > 1) {
                mode = input[1];
            }

            SpotifyRequest spotifyRequest = new SpotifyRequest(resource);

            switch (pick) {
                case "new":
                    client.setCurrentPage(0);
                    spotifyRequest.requestToSpotify(clientToken, NEW_API_PATH);
                    NewAlbumsJsonParser alb = new NewAlbumsJsonParser(spotifyRequest.getResponse());
                    client.setRecord(alb.splitRecordsToPages(alb.getRecords(), page));
                    client.printRecord();
                    break;
                case "featured":
                    client.setCurrentPage(0);
                    spotifyRequest.requestToSpotify(clientToken, FEATURED_API_PATH);
                    FeaturedJsonParser ftr = new FeaturedJsonParser(spotifyRequest.getResponse());
                    client.setRecord(ftr.splitRecordsToPages(ftr.getRecords(), page));
                    client.printRecord();
                    break;
                case "categories":
                    client.setCurrentPage(0);
                    spotifyRequest.requestToSpotify(clientToken, CATEGORIES_API_PATH);
                    CategoriesJsonParser ctg = new CategoriesJsonParser(spotifyRequest.getResponse());
                    client.setRecord(ctg.splitRecordsToPages(ctg.getRecords(), page));
                    client.printRecord();
                    break;
                case "playlists":
                    client.setCurrentPage(0);
                    spotifyRequest.requestToSpotifyById(clientToken, CATEGORIES_API_PATH, mode);
                    FeaturedJsonParser ftrById = new FeaturedJsonParser(spotifyRequest.getResponse());
                    client.setRecord(ftrById.splitRecordsToPages(ftrById.getRecords(), page));
                    client.printRecord();
                    break;
                case "prev":
                    client.prevPage();
                    break;
                case "next":
                    client.nextPage();
                    break;
                case "exit":
                    exit();
                    break;
            }
        }
    }

    private static void exit() {
        System.out.println("---GOODBYE!---");
        System.exit(0);
    }
}
