import ReusableMethods.javaMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class basics {

    public static String place_id; // declared as static so that variable can be accessed from another class using "className.variableName"
    public static String address = "70 Courtyard, USA";

    public static void main(String[] args) throws IOException {
        //Add place API: POST Request, Validating status code, header and response body
        RestAssured.baseURI ="https://rahulshettyacademy.com/";
        //convert content from the file to Byte -> covert byte data into string in body method
        String responseBody = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body(new String(Files.readAllBytes(Paths.get("C:\\Users\\PrajithThacharazhiya\\OneDrive - G10X\\Documents\\personal docs\\Learnings\\REST ASSURED\\addplace.json"))))
                .when().post("maps/api/place/add/json") //resource inside the post method with automatically concatinated with the baseURI in the backend
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.52 (Ubuntu)")).extract().response().asString();
        JsonPath js = javaMethods.stringToJson(responseBody);//calling java method to convert the string to json
        place_id = js.getString("place_id");//get value from the response json body




        //update place: PUT Request
        given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\n" +
                        "\"place_id\":\""+place_id+"\",\n" +
                        "\"address\":\""+address+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //get place: GET Request
        String getResponseBody = given().log().all().queryParams("key", "qaclick123")
                .queryParam("place_id", place_id)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath js1 = javaMethods.stringToJson(getResponseBody);
        String getAddress = js1.getString("address");
        Assert.assertEquals(address, getAddress);
    }
}
