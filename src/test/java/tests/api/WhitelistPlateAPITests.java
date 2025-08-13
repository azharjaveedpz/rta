package tests.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pages.api.WhitelistPlateAPIPage;
import tests.ui.TestLaunchBrowser;
import utils.DataReader;
import utils.PayloadGenerator;
import utils.PayloadGeneratorForDate;
import utils.PayloadGeneratorForDublicate;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;


public class WhitelistPlateAPITests extends BaseTest{

	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);
    private static final String BASE_URL = "https://devparkingapi.kandaprojects.live/api/WhitelistPlate";
    WhitelistPlateAPIPage apiPage = new WhitelistPlateAPIPage();

    // Helper method to send request and validate status code
  
        @Test
        public void TC01_ValidRecordCreation() throws Exception {
            logger.info("Running Test: TC01_ValidRecordCreation");

            PayloadGenerator generator = new PayloadGenerator();
            String payload = generator.generatePlatePayload();

            Response response = apiPage.sendRequest(payload);

            int statusCode = response.getStatusCode();
            String responseBody = response.asPrettyString();
            long responseTime = response.getTime();

            logger.info("Response Status: {}", statusCode);
            logger.info("Response Body: {}", responseBody);
            logger.info("Response Time: {} ms", responseTime);

            test.log(Status.INFO, "Response Code: " + statusCode);
            test.log(Status.INFO, "Response Time: " + responseTime + " ms");
            test.log(Status.INFO, "Response Body:<br><pre>" + responseBody + "</pre>");

            // Status code check
            if (statusCode == 200 || statusCode == 201) {
                test.log(Status.PASS, "Valid record created successfully. Status: " + statusCode);
            } else {
                test.log(Status.FAIL, "Expected 200 or 201 but got " + statusCode);
                throw new AssertionError("Expected status code 200/201 but got " + statusCode);
            }

            // ID validation
            String id = response.jsonPath().getString("data.id");
            if (id != null && !id.isEmpty()) {
                logger.info("Record ID generated: {}", id);
                test.log(Status.PASS, "Record ID generated: " + id);
            } else {
                test.log(Status.FAIL, "No ID generated in the response.");
                throw new AssertionError("No ID generated in the response.");
            }

            // Success message validation
            String successMessage = response.jsonPath().getString("en_Msg");
            if ("Record created successfully".equalsIgnoreCase(successMessage)) {
                test.log(Status.PASS, "Success message validated: " + successMessage);
            } else {
                test.log(Status.FAIL, "Unexpected success message: " + successMessage);
                throw new AssertionError("Unexpected success message: " + successMessage);
            }
        }

        @Test


        public void TC02_ValidRecordCreationWithInvalidDate() throws Exception {
            logger.info("Running Test: TC02_InvalidDate");

            PayloadGeneratorForDate generator = new PayloadGeneratorForDate();
            String payload = generator.generatePlatePayloadForDate();

            Response response = apiPage.sendRequest(payload);

            int statusCode = response.getStatusCode();
            String responseBody = response.asPrettyString();
            long responseTime = response.getTime();

            logger.info("Response Status: {}", statusCode);
            logger.info("Response Body: {}", responseBody);

            test.log(Status.INFO, "Response Code: " + statusCode);
            test.log(Status.INFO, "Response Time: " + responseTime + " ms");
            test.log(Status.INFO, "Response Body:<br><pre>" + responseBody + "</pre>");

            JsonPath jsonPath = new JsonPath(responseBody);
            String actualMessage = jsonPath.getString("validationErrors[0].enMessage");

            if (statusCode == 428 && "From date cannot be after To date".equals(actualMessage)) {
                test.log(Status.PASS, "API correctly rejected invalid dates with correct message.");
            } else {
                test.log(Status.FAIL, "Expected status 428 and message 'From date cannot be after To date', but got Status: "
                        + statusCode + ", Message: " + actualMessage);
                throw new AssertionError("Validation failed: Status=" + statusCode + ", Message=" + actualMessage);
            }
        }

        @Test
        public void TC03_ValidRecordCreationWithDuplicateData() throws Exception {
            logger.info("Running Test: TC03_DublicateRecord");

            PayloadGeneratorForDublicate generator = new PayloadGeneratorForDublicate();
            String payload = generator.generatePlatePayloadForDub();

            Response response = apiPage.sendRequest(payload);

            int statusCode = response.getStatusCode();
            String responseBody = response.asPrettyString();
            long responseTime = response.getTime();

            logger.info("Response Status: {}", statusCode);
            logger.info("Response Body: {}", responseBody);

            test.log(Status.INFO, "Response Code: " + statusCode);
            test.log(Status.INFO, "Response Time: " + responseTime + " ms");
            test.log(Status.INFO, "Response Body:<br><pre>" + responseBody + "</pre>");

            JsonPath jsonPath = new JsonPath(responseBody);
            String actualMessage = jsonPath.getString("en_Msg");

            if (statusCode == 409 && "Plate number already exists for the specified period".equals(actualMessage)) {
                test.log(Status.PASS, "API correctly rejected invalid dates with correct message.");
            } else {
                test.log(Status.FAIL, "Expected status 428 and message 'From date cannot be after To date', but got Status: "
                        + statusCode + ", Message: " + actualMessage);
                throw new AssertionError("Validation failed: Status=" + statusCode + ", Message=" + actualMessage);
            }
        }


   
}