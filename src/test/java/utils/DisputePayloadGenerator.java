package utils;

import com.github.javafaker.Faker;

import pages.api.DisputeManagementAPI;

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



import com.github.javafaker.Faker;
import org.json.JSONObject;

public class DisputePayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a default dispute payload with random but valid values.
     */
    public static String defaultDisputePayload() {
        JSONObject payload = new JSONObject();
        payload.put("fineId", 1);
        payload.put("department", 1);
        payload.put("payment_Type", 1);
        payload.put("dispute_Reason", "Vehicle wrongly fined");
        payload.put("crM_Ref", faker.regexify("[A-Z0-9]{8,12}"));
        payload.put("email", faker.internet().emailAddress());
        payload.put("phone", faker.number().digits(10));
        payload.put("address", faker.address().fullAddress());

        return payload.toString();
    }
}
