package files;

import ReusableMethods.javaMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class dynamicJson {

    @Test(dataProvider = "BookData")
    public void addBook(String ISBN, String AISLE) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json").
                body(payload.addBook(ISBN, AISLE)).
                when().post("/Library/Addbook.php").
                then().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = javaMethods.stringToJson(response);
        String id = js.getString("ID");
        System.out.println(response);
        System.out.println(id);
    }

    @DataProvider(name = "BookData")
    public Object[][] getData(){
        return new Object[][] {{"java", "1"}, {"manual", "2"}, {"ApiTesting", "3"}};
    }

}