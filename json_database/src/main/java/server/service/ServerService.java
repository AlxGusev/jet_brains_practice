package server.service;

import client.Request;
import com.google.gson.Gson;
import server.Response;
import server.Status;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerService {

    private final String FILE_PATH = "./src/main/java/server/data/db.json";
    private final String REASON = "No such key";
    public boolean exit = false;
    private final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    public Response parseString(String request) {

        Gson gson = new Gson();
        Request msg = gson.fromJson(request, Request.class);

        switch (msg.getType()) {
            case "get":
                return readFromFile(msg.getKey());
            case "set":
                return writeToFile(msg);
            case "delete":
                return deleteFromFile(msg.getKey());
            case "exit":
                Response response = new Response();
                response.setStatus(Status.OK);
                exit = true;
                return response;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Response writeToFile(Request request) {

        Response response = new Response();

        try (Writer writer = Files.newBufferedWriter(Path.of(FILE_PATH))) {

            Gson gson = new Gson();
            gson.toJson(request, writer);
            response.setStatus(Status.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response readFromFile(String key) {

        Lock readLock = LOCK.readLock();
        readLock.lock();
        Response response = new Response();
        try (Reader reader = Files.newBufferedReader(Path.of(FILE_PATH))) {

            Gson gson = new Gson();
            Request req = gson.fromJson(reader, Request.class);

            if (req != null && key.equals(req.getKey())) {
                response.setStatus(Status.OK);
                response.setValue(req.getValue());
            } else {
                response.setStatus(Status.ERROR);
                response.setReason(REASON);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        readLock.unlock();
        return response;
    }

    public Response deleteFromFile(String key) {

        Lock writeLock = LOCK.writeLock();
        writeLock.lock();

        Response response = new Response();

        if (readFromFile(key).getResponse() == Status.OK) {
            try (Writer writer = Files.newBufferedWriter(Path.of(FILE_PATH))) {
                writer.write("");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.setStatus(Status.OK);
        } else {
            response.setStatus(Status.ERROR);
            response.setReason(REASON);
        }
        writeLock.unlock();
        return response;
    }
}
