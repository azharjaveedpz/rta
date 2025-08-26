package utils;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import org.json.JSONObject;

import com.github.javafaker.Faker;
import org.json.JSONObject;

import com.github.javafaker.Faker;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import java.time.LocalDate;

import org.json.JSONObject;
import java.time.LocalDate;

public class PayloadGenerator {

	// Default valid payload
	public static String generateDefaultPlatePayload() {
		Faker faker = new Faker();
		String plateNumber = faker.regexify("[A-Z]{2}[0-9]{5}[A-Z]{3}");
		JSONObject payload = new JSONObject();
		payload.put("plateNumber", plateNumber);
		payload.put("plateSource_Id", 1);
		payload.put("plateType_Id", 1);
		payload.put("plateColor_Id", 1);
		payload.put("plateStatus_Id", 1);
		payload.put("exemptionReason_ID", 1);
		payload.put("isByLaw", true);

		payload.put("fromDate", LocalDate.now().toString() + "T00:00:00.000Z");
		payload.put("toDate", LocalDate.now().plusDays(5).toString() + "T23:59:59.000Z");

		return payload.toString();
	}

	// Today's date
	public static String generateTodaysDatePayload() {
		JSONObject payload = new JSONObject(generateDefaultPlatePayload());
		payload.put("fromDate", LocalDate.now().toString() + "T00:00:00.000Z");
		payload.put("toDate", LocalDate.now().plusDays(2).toString() + "T23:59:59.000Z");
		return payload.toString();
	}

	// Duplicate plate (same as default)
	public static String generateDuplicatePlatePayload() {
		JSONObject payload = new JSONObject(generateDefaultPlatePayload());
		payload.put("plateNumber", "KA01AB1234");
		return payload.toString();
	}

	// In PayloadGenerator
	public static String generateInactiveStatusPayload() {
		Faker faker = new Faker();
		String plateNumber = faker.regexify("[A-Z]{2}[0-9]{5}[A-Z]{3}");

		JSONObject payload = new JSONObject();
		payload.put("plateNumber", plateNumber);
		payload.put("plateSource_Id", 1);
		payload.put("plateType_Id", 1);
		payload.put("plateColor_Id", 1);
		payload.put("plateStatus_Id", 2);
		payload.put("exemptionReason_ID", 1);
		payload.put("isByLaw", true);

		payload.put("fromDate", LocalDate.now().toString() + "T00:00:00.000Z");
		payload.put("toDate", LocalDate.now().plusDays(5).toString() + "T23:59:59.000Z");

		return payload.toString();
	}

	public static String generateisByLawPlatePayload() {
		JSONObject payload = new JSONObject(generateDefaultPlatePayload());
		payload.put("isByLaw", false);
		return payload.toString();
	}

	public static String generateEditPlatePayload(int idValue) {
		Faker faker = new Faker();
		String plateNumber = faker.regexify("[A-Z]{2}[0-9]{5}[A-Z]{3}");

		JSONObject payload = new JSONObject();
		payload.put("id", idValue);

		payload.put("plateNumber", plateNumber);
		payload.put("plateSource_Id", 1);
		payload.put("plateType_Id", 1);
		payload.put("plateColor_Id", 1);
		payload.put("plateStatus_Id", 1);
		payload.put("exemptionReason_ID", 1);
		payload.put("isByLaw", true);

		payload.put("fromDate", LocalDate.now().toString() + "T00:00:00.000Z");
		payload.put("toDate", LocalDate.now().plusDays(5).toString() + "T23:59:59.000Z");

		payload.put("editBy", "string");

		return payload.toString();
	}

}
