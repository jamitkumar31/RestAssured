package Library;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class DynamicJson {

    @DataProvider(name="BookData")
    public Object[][] getData(){
        return new Object[][] {{"Qwerty","456"},{"asdfg","3456"},{"zxcvb","7890"}};
    }

    @Test(dataProvider = "BookData" , priority = 0)
    public void addBook(String isbn,String aisle){
        RestAssured.baseURI="http://216.10.245.166";

        // Add the book
        String response=given().log().all().header("Content-Type","application/json")
                .body(payload.Addbook(isbn,aisle))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js=ReUsableMethods.rawToJson(response);
        String id=js.get("ID");
        //Assert.assertEquals(id,"bcd227");

    }
    @Test(dataProvider ="BookData" ,priority = 1)
    public void deleteBook(String isbn,String aisle){
        RestAssured.baseURI="http://216.10.245.166";

        // Add the book
        String response=given().log().all().header("Content-Type","application/json")
                .body("{\n" +
                        " \n" +
                        "\"ID\" : \""+isbn+""+aisle+"\"\n" +
                        "\n" +
                        "} ")
                .when().post("Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js=ReUsableMethods.rawToJson(response);
        String msg=js.get("msg");
        System.out.println(msg);
        //Assert.assertEquals(id,"bcd227");

    }

}
