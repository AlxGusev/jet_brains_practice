package SpotifyRequest;

import JsonParser.CategoriesJsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class SpotifyRequest {

    private final String resource;
    private String response;

    public SpotifyRequest(String resource) {
        this.resource = resource;
    }

    public void requestToSpotify(String accessToken, String apiPath) {

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(resource + apiPath))
                .GET()
                .build();
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        }  catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void requestToSpotifyById(String accessToken, String apiPath, String mode) {

        requestToSpotify(accessToken, apiPath);
        CategoriesJsonParser categories = new CategoriesJsonParser(response);
        List<String> names = categories.getCategoryNames();
        String playListId;

        if (!names.contains(mode)) {
            System.out.println("Unknown category name.");
        } else {
            int index = names.indexOf(mode);
            playListId = categories.getCategoryIds(index);

            String playlistApiPath = apiPath + "/" + playListId + "/playlists";
            requestToSpotify(accessToken, playlistApiPath);
            if (response.contains("error")) {
                System.out.println(response);
            }
        }
    }

    public String getResponse() {
        return response;
    }

}
