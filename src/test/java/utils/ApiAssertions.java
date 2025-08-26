package utils;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;

public class ApiAssertions {

 public static void assertStatusCode(Response response, int expectedCode, String successMsg, String failMsg,
                                     ExtentTest test, org.apache.logging.log4j.Logger logger) {
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

 public static void assertResponseContains(Response response, String expectedValue, String successMsg, String failMsg,
                                           ExtentTest test, org.apache.logging.log4j.Logger logger) {
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
