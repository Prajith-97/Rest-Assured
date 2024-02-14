package ReusableMethods;

import io.restassured.path.json.JsonPath;

public class javaMethods {

    public static JsonPath stringToJson(String response){
        JsonPath js = new JsonPath(response);//parsing response body string to JsonPath class convert it into json and store value in js object
        return js;
    }
}
