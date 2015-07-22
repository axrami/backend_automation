package json;

import org.json.simple.JSONObject;

/**
 * Created by andrew on 7/22/15.
 */
public class JsonGen {
    public String createJsonString(String jsonString) {
        JSONObject obj = new JSONObject();

        return obj.toString();
    }
}
