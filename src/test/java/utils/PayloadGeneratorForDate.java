package utils;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

public class PayloadGeneratorForDate {

	public static String generatePlatePayloadForDate() {
	    Faker faker = new Faker();

	    String plateNumber = faker.regexify("[A-Z]{2}[0-9]{3}[A-Z]{1}[0-9]{1}[A-Z]{2}");

	    List<String> uaeCities = Arrays.asList("Dubai", "Abu Dhabi", "Sharjah", "Ajman", "Fujairah", "Ras Al Khaimah", "Umm Al Quwain");
	    String plateSource = uaeCities.get(faker.number().numberBetween(0, uaeCities.size()));

	    String plateColor = faker.color().name();

	    List<String> type = Arrays.asList("Private", "Commercial", "Motorcycle", "Taxi");
	    String plateType = type.get(faker.number().numberBetween(0, type.size()));

	    String fromDate = "2025-08-15T22:13:06.406Z";
	    String toDate = "2025-08-13T22:13:06.406Z";

	    // Instead of strings, use numeric IDs
	    List<Integer> exemptionReasonIDs = Arrays.asList(1, 2, 3);
	    int exemptionReason_ID = exemptionReasonIDs.get(faker.number().numberBetween(0, exemptionReasonIDs.size()));

	    // Adding the missing "value" field
	    int value = faker.number().numberBetween(1, 1000);

	    JSONObject payload = new JSONObject();
	    payload.put("plateNumber", plateNumber);
	    payload.put("plateSource", plateSource);
	    payload.put("plateType", plateType);
	    payload.put("plateColor", plateColor);
	    payload.put("fromDate", fromDate);
	    payload.put("toDate", toDate);
	    payload.put("plateStatus", "Active");
	    payload.put("value", value); // required field
	    payload.put("exemptionReason_ID", exemptionReason_ID); // now integer

	    return payload.toString();
	}


}
