package tests.api;

import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
@Test
public class TestAPI {
	  Response response = given()
		        .header("Content-Type", "application/json")
		        .body("{ \"name\": \"John\", \"job\": \"QA\" }")
		    .when()
		        .post("https://reqres.in/api/users")
		    .then()
		        .statusCode(201)
		        .extract().response();

		    String userId = response.jsonPath().getString("id");

		}
		