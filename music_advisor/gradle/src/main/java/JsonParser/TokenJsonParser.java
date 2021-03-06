package JsonParser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TokenJsonParser implements SpotifyJsonParserInterface{

    private final String jsonString;
    private String token;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;

    public TokenJsonParser(String jsonString) {
        this.jsonString = jsonString;
        parseJsonString();
    }

    @Override
    public void parseJsonString() {
        JsonObject jo = JsonParser.parseString(jsonString).getAsJsonObject();
        token = jo.get("access_token").getAsString();
        tokenType = jo.get("token_type").getAsString();
        expiresIn = jo.get("expires_in").getAsString();
        refreshToken = jo.get("refresh_token").getAsString();
    }

    public String getToken() {
        return token;
    }
}
