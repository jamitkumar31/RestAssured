package files;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {
    public static JsonPath rawToJson(String getPlacehold){
        JsonPath js =new JsonPath(getPlacehold);
        return js;
    }
}
