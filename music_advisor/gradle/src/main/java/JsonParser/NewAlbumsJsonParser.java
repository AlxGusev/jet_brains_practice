package JsonParser;

import Paging.PagingInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class NewAlbumsJsonParser implements SpotifyJsonParserInterface, PagingInterface {

    private final String jsonString;
    private final List<List<String>> records = new ArrayList<>();

    public NewAlbumsJsonParser(String jsonString) {
        this.jsonString = jsonString;
        parseJsonString();
    }

    @Override
    public void parseJsonString() {
        JsonObject jo = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject albums = jo.getAsJsonObject("albums");
        JsonArray items = albums.getAsJsonArray("items");

        for (JsonElement elem : items) {

            List<String> rec = new ArrayList<>();

            JsonObject item = elem.getAsJsonObject();
            JsonArray artists = item.getAsJsonArray("artists");

            StringJoiner joiner = new StringJoiner(", ");

            for (JsonElement artist: artists) {
                JsonObject artistItem = artist.getAsJsonObject();
                joiner.add(artistItem.get("name").getAsString());
            }

            JsonObject external_urls = item.get("external_urls").getAsJsonObject();

            rec.add(item.get("name").getAsString());
            rec.add("[" + joiner.toString() + "]");
            rec.add(external_urls.get("spotify").getAsString());

            records.add(rec);
        }
    }

    public List<List<String>> getRecords() {
        return records;
    }

}
