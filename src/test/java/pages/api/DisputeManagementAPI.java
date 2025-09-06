package pages.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.PayloadGenerator;

import static io.restassured.RestAssured.given;



import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;



import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class DisputeManagementAPI {

    private static final String BASE_URL = "https://devparkingapi.kandaprojects.live/api";
    private static final String DISPUTE_ENDPOINT = "/Dispute/Create";

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(DisputeManagementAPI.class);

    static {
        RestAssured.baseURI = BASE_URL;
    }

    // ===================== POST =====================
	public Response sendRequest(String payload) {
		return given().header("accept", "text/plain").header("Content-Type", "application/json").body(payload).when()
				.post(DISPUTE_ENDPOINT).then().extract().response();
	}
	/**
	 * Builds cURL command string for POST.
	 */
	public String buildCurlCommand(String payload) {
		return "curl -X POST \"" + BASE_URL + DISPUTE_ENDPOINT + "\" " + "-H \"accept: text/plain\" "
				+ "-H \"Content-Type: application/json\" " + "-d '" + payload.replace("'", "\\'") + "'";
	}
	/**
	 * Combined POST: send request + log everything.
	 */
	public Response sendAndLogRequest(String payload, ExtentTest test, org.apache.logging.log4j.Logger customLogger) {
		Response response = sendRequest(payload);
		logRequestAndResponse(buildCurlCommand(payload), response, test, customLogger);
		return response;
	}
	// ===================== LOGGING =====================
		/**
		 * Logs request + response to both logger & Extent report.
		 */
		private void logRequestAndResponse(String curlCommand, Response response, ExtentTest test,
				org.apache.logging.log4j.Logger customLogger) {
			int statusCode = response.getStatusCode();
			String responseBody = response.asPrettyString();
			long responseTime = response.getTime();

			// Console / Logger logs
			customLogger.info("Request cURL: {}", curlCommand);
			customLogger.info("Response Status: {}", statusCode);
			customLogger.info("Response Time: {} ms", responseTime);
			customLogger.info("Response Body:\n{}", responseBody);

			// Extent Report logs
			test.log(Status.INFO, "Request cURL:<br><pre>" + curlCommand + "</pre>");
			test.log(Status.INFO, "Response Code: " + statusCode);
			test.log(Status.INFO, "Response Time: " + responseTime + " ms");
			test.log(Status.INFO, "Response Body:<br><pre>" + responseBody + "</pre>");
		}
		
		public class DisputeAssertions {

			public static void assertStatusCode(Response response, int expectedCode, String successMsg, String failMsg,
					ExtentTest test, Logger logger) {
				try {
					Assert.assertEquals(response.getStatusCode(), expectedCode, failMsg);
					test.pass("✅ " + successMsg + " (Status: " + expectedCode + ")");
					logger.info(successMsg + " | Actual status: " + response.getStatusCode());
				} catch (AssertionError e) {
					test.fail("❌ " + failMsg + " | Actual status: " + response.getStatusCode());
					logger.error(failMsg + " | Response: " + response.asString(), e);
					throw e;
				}
			}

			public static void assertResponseContains(Response response, String expectedValue, String successMsg,
					String failMsg, ExtentTest test, Logger logger) {
				try {
					Assert.assertTrue(response.asString().contains(expectedValue), failMsg);
					test.pass("✅ " + successMsg + " | Found: " + expectedValue);
					logger.info(successMsg + " | Value present in response");
				} catch (AssertionError e) {
					test.fail("❌ " + failMsg + " | Missing: " + expectedValue);
					logger.error(failMsg + " | Response: " + response.asString(), e);
					throw e;
				}
			}
		}

}