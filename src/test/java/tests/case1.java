package tests;

import Base.BaseClass;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.proxy;
import static org.hamcrest.Matchers.equalTo;


public class case1 extends BaseClass {
    private static String listID;
    private static String cardID_1;
    private static String cardID_2;
    private static String selectedCardId;
    protected static String boardID;


    @Test(priority = 0)
    public void testCreateBoardAndGetId() {


        System.out.println(boardID);
        Response response=given().spec(requestSpec).queryParams("name","testinium1").when()
                .post("1/boards");
        response.prettyPrint();
        boardID=response.jsonPath().getString("id");




    }

  @Test(dependsOnMethods = "createBoard",priority = 1)
    public void createAlist(){
      String boardİd=boardID;


        listID= given().spec(requestSpec)
                .pathParam("id", boardİd)
                .queryParam("name","list1")
                .when().post("1/boards/{id}/lists").
                then().log().all().assertThat().statusCode(200).extract().path("id");
        //657059ae6c1c998a7a8c459b




    }
    @Test(dependsOnMethods = "createBoard",priority = 2)
    public void createACart(){

         cardID_1= given().spec(requestSpec)
                .queryParam("name", "card")
                .queryParam("idList", listID)
                .when().post("1/cards").
                then().log().all().assertThat().statusCode(200).assertThat().body("name", equalTo("card"))
                .extract().path("id");
         cardID_2=given().spec(requestSpec)
                 .queryParam("name", "card")
                 .queryParam("idList", listID)
                 .when().post("1/cards").
                 then().log().all().assertThat().statusCode(200).assertThat().body("name", equalTo("card"))
                 .extract().path("id");
        int randomNumber = new Random().nextInt(2);


         selectedCardId = (randomNumber == 0) ? cardID_1 : cardID_2;

        System.out.println("Selected Card ID: " + selectedCardId);


    }


    @Test(dependsOnMethods = "createBoard",priority = 3)
    private void updateCart(){
        Response response=given()
                .spec(requestSpec)
                .queryParams("name","updateTest")
                .put("/1/cards/{id}", selectedCardId);
        response.then().assertThat().statusCode(200);
    }

    @Test(dependsOnMethods = "createBoard",priority = 4)
    public void deletecart(){
        Response response=given().spec(requestSpec).delete("/1/cards/{id}", cardID_1);
        response.then().assertThat().statusCode(200);
        Response response1=given().spec(requestSpec).delete("/1/cards/{id}", cardID_2);
        response.then().assertThat().statusCode(200);




    }



    @Test(dependsOnMethods = "createBoard",priority = 5)
    public void deleteBoard() {

        Response response=given().spec(requestSpec).delete("/1/boards/{id}", boardID);
        response.then().assertThat().statusCode(200);





    }





}
