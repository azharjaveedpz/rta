package tests.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pages.api.DisputeManagementAPI;
import pages.api.DisputeManagementAPI.DisputeAssertions;
import pages.api.WhitelistPlateAPI;
import tests.ui.TestLaunchBrowser;
import utils.ApiAssertions;
import utils.DataReader;
import utils.DisputePayloadGenerator;
import utils.PayloadGenerator;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;


import com.aventstack.extentreports.ExtentTest;



import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import utils.DisputePayloadGenerator;

public class DisputeManagementAPITest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(DisputeManagementAPITest.class);
    private final DisputeManagementAPI disputeApi = new DisputeManagementAPI();

    @Test
	public void TC01_shouldCreateDisputeSuccessfully() {
		String payload = DisputePayloadGenerator.defaultDisputePayload();
		Response response = disputeApi.sendAndLogRequest(payload, test, logger);

		ApiAssertions.assertStatusCode(response, 201, "Dispute created successfully", "Dispute creation failed", test,
				logger);

		 String disputeId = response.jsonPath().getString("data.dispute_Id");

		    // Check dispute_Id is not null or empty
		    if (disputeId != null && !disputeId.isEmpty()) {
		        logger.info("Dispute ID is present: {}", disputeId);
		        test.pass("Dispute ID is present: " + disputeId);
		    } else {
		        logger.error("dispute_Id missing in response");
		        test.fail("dispute_Id missing in response");
		        Assert.fail("dispute_Id missing in response");
		    }
	}
}
