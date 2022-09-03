import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basic {
	public static void main(String[] args) {
		//Base url
		RestAssured.baseURI="https://rahulshettyacademy.com";
		//Query parameter
		String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(payload.addplace())
		//submit the api resource ,http method
				//log().all() print all body
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();

		System.out.println(response);
		JsonPath js=new JsonPath(response);// for parsing Json
		String placeId=js.getString("place_id");
		System.out.println(placeId);

		//update place
		given().log().all().queryParam("key","qaclick123").header("content-Type","application/json")
		.body("{\n" +
				"\"place_id\":\""+placeId+"\",\n" +
				"\"address\":\"70 Summer walk, USA\",\n" +
				"\"key\":\"qaclick123\"\n" +
				"}")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));

		//Add place

		String getPlaceholder=given().log().all().queryParam("key","qaclick123")
				.queryParam("place_id",placeId)
		.when().get("/maps/api/place/get/json")
		.then().statusCode(200).log().all().assertThat().extract().response().asString();
		JsonPath js1= ReUsableMethods.rawToJson(getPlaceholder);
		String actualAdress=js1.getString("address");
		System.out.println(actualAdress);
		Assert.assertEquals(actualAdress,"70 Summer walk, USA");
	}


}
