package pages.api;

import io.restassured.response.Response;
import utils.PayloadGenerator;

import static io.restassured.RestAssured.given;

public class WhitelistPlateAPIPage {

    public Response sendRequest(String payload) {
        return given()
                .header("Content-Type", "application/json")
                .accept("text/plain")
                .body(payload)
                .when()
                .post("https://devparkingapi.kandaprojects.live/api/WhitelistPlate");
    }

    public static void main(String[] args) {
        WhitelistPlateAPIPage apiPage = new WhitelistPlateAPIPage();
        String payload = PayloadGenerator.generatePlatePayload();
        Response response = apiPage.sendRequest(payload);
        System.out.println("Response: " + response.prettyPrint());
    }
}

