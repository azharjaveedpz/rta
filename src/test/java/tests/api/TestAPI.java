package tests.api;

import io.restassured.response.Response;
import tests.ui.TestLaunchBrowser;

import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.Status;

import base.BaseTest;

import static io.restassured.RestAssured.given;

public class TestAPI extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void createUserUnauthorizedTest() {
	    logger.info("Testing unauthorized user creation");

	    Response response = given()
	            .header("Content-Type", "application/json")
	            .body("{ \"name\": \"John\", \"job\": \"QA\" }")
	            .when()
	            .post("https://reqres.in/api/users");

	    int statusCode = response.getStatusCode();

	    if (statusCode == 401) {
	        String errorMsg = "Unauthorized access - Status Code: " + statusCode;
	        logger.error(errorMsg);
	        test.log(Status.FAIL, errorMsg);
	    } else {
	        test.log(Status.PASS, "Request succeeded unexpectedly with status: " + statusCode);
	    }
	}
}