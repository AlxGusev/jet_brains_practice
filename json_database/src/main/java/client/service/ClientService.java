package client.service;

import client.Request;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientService {

    private final String CLIENT_FILE_PATH = "./src/main/java/client/data/";

    public String parseRequest(Request request) throws IOException {

        if (request.getFile() != null) {

            Request req = readClientFile(request.getFile());
            if (!(req == null)) {
                return req.toJson();
            } else {
                throw new IOException();
            }
        }
        return request.toJson();
    }

    public Request readClientFile(String filePath) {
        Request request = null;
        try (Reader reader = Files.newBufferedReader(Path.of(CLIENT_FILE_PATH + filePath))) {
            Gson gson = new Gson();
            request = gson.fromJson(reader, Request.class);
        } catch (IOException e) {
            System.out.println("File: " + filePath + " doesn't exist");
        }
        return request;
    }
}
