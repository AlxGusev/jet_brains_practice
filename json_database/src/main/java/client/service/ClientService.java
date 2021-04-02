package client.service;

import client.Request;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientService {

    private final String CLIENT_FILE_PATH = "./src/main/java/client/data/";

    public String parseRequest(Request request) throws IOException {

        if (request.getFile() != null) {
            return readClintFile(request.getFile());
        }
        return request.toJson();
    }

    public String readClintFile(String fileName) {

        String jo = "";

        try (Reader reader = Files.newBufferedReader(Path.of(CLIENT_FILE_PATH + fileName))) {

            jo = JsonParser.parseReader(reader).toString();

        } catch (IOException e) {
            System.out.println("File: " + fileName + " doesn't exist");
        }
        return jo;
    }
}
