package JsonParser;

import Paging.PagingInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class CategoriesJsonParser implements SpotifyJsonParserInterface, PagingInterface {

    private final String jsonString;
    private final List<List<String>> records = new ArrayList<>();
    private final List<String> categoryNames = new ArrayList<>();
    private final List<String> categoryIds = new ArrayList<>();

    public CategoriesJsonParser(String jsonString) {
        this.jsonString = jsonString;
        parseJsonString();
    }

    @Override
    public void parseJsonString() {

        JsonObject jo = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject categories = jo.getAsJsonObject("categories");
        JsonArray items = categories.getAsJsonArray("items");
        for (JsonElement elem : items) {

            List<String> rec = new ArrayList<>();

            JsonObject item = elem.getAsJsonObject();

            categoryNames.add(item.get("name").getAsString());
            categoryIds.add(item.get("id").getAsString());
            rec.add(item.get("name").getAsString());

            records.add(rec);
        }
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public String getCategoryIds(int index) {
        return categoryIds.get(index);
    }
}
