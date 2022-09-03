import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(payload.BookPrice());

        //Print No of courses returned by API
        int count = js.getInt("courses.size()");
        System.out.println(count);

        //print purchase amount
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //print title of first page
        String FirstTitle = js.getString("courses[0].title");
        System.out.println(FirstTitle);

        //Print All course titles and their respective Prices
        for (int i = 0; i < count; i++) {
            System.out.print(js.getString("courses[" + i + "].title") + " ");
            System.out.println(js.getInt("courses[" + i + "].price"));
        }

        //print no of copies sold by RPA Course
        for (int i = 0; i < count; i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies=js.getInt("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }

        }
        //Verify if Sum of all Course prices matches with Purchase Amount
        int totalPrice=0;
        for (int i = 0; i < count; i++) {
            int coursePrice = js.get("courses[" + i + "].price");
            int courseCopies = js.get("courses[" + i + "].copies");
            totalPrice += coursePrice*courseCopies;
        }
        if(totalAmount==totalPrice){
            System.out.println("price matched with dashboard purchase price");
        }


    }
}
