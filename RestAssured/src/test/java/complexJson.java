import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.Objects;

public class complexJson {

    static int total = 0;
    public static void main(String[] args){

        JsonPath js = new JsonPath(payload.sampleJson());
        int count = js.getInt("courses.size()");
        System.out.println(count);
        //print purchase amount
        int amount = js.get("dashboard.purchaseAmount");
        System.out.println(amount);
        //course name
        String name = js.get("courses[0].title");
        System.out.println(name);
        //print all courses and prices
        for (int i =0;i<count;i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            int coursePrice = js.get("courses[" + i + "].price");
            System.out.println(courseTitle);
            System.out.println(coursePrice);

        }
        // to get copies of particular course
        for (int i =0;i<count;i++){
            String courseTitle = js.get("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies = js.get("courses[" + i + "].copies");
                System.out.println(copies);
            }
        }
        //to ensure the purchased amount matches the product of copies & price
        for (int i =0;i<count;i++){
            int copies = js.get("courses[" + i + "].copies");
            int price = js.get("courses[" + i + "].price");
            int totalAmount =  copies*price;
            total = total+totalAmount;
        }
        Assert.assertEquals(total,amount);


    }
}
