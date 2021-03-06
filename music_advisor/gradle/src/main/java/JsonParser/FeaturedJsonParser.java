package JsonParser;

import Paging.PagingInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class FeaturedJsonParser implements SpotifyJsonParserInterface, PagingInterface {

    private final String jsonString;
    private final List<List<String>> records = new ArrayList<>();

    public FeaturedJsonParser(String jsonString) {
        this.jsonString = jsonString;
        parseJsonString();
    }

    @Override
    public void parseJsonString() {
        JsonObject jo = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject playlists = jo.get("playlists").getAsJsonObject();
        JsonArray items = playlists.getAsJsonArray("items");

        for (JsonElement elem : items) {

            List<String> rec = new ArrayList<>();

            JsonObject item = elem.getAsJsonObject();
            JsonObject external_urls = item.get("external_urls").getAsJsonObject();

            rec.add(item.get("name").getAsString());
            rec.add(external_urls.get("spotify").getAsString());

            records.add(rec);
        }
    }

    public List<List<String>> getRecords() {
        return records;
    }
}
