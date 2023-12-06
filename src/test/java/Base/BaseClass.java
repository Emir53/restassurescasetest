package Base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeClass;

public class BaseClass {
    protected static RequestSpecification requestSpec;



    @BeforeClass
     public  void createRequestSpecifications(){

        requestSpec=new RequestSpecBuilder()
                .setBaseUri("https://api.trello.com")
                .addQueryParam("token","TOKEN")
                .addQueryParam("key","APİKEY")
                .addHeader("Content-Type","application/json")
                .build();
    }
}
