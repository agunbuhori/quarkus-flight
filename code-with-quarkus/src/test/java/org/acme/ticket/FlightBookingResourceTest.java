package org.acme.ticket;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class FlightBookingResourceTest {

    @Test
    void shouldCreateGetListAndDeleteBooking() {
        long id = given()
                .contentType(ContentType.JSON)
                .body(validBookingPayload("Andi", "GA-100"))
                .when().post("/api/bookings")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("status", equalTo("CONFIRMED"))
                .extract()
                .jsonPath()
                .getLong("id");

        given()
                .when().get("/api/bookings/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo((int) id))
                .body("passengerName", equalTo("Andi"))
                .body("flightNumber", equalTo("GA-100"));

        given()
                .when().get("/api/bookings")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("id", hasItem((int) id));

        given()
                .when().delete("/api/bookings/{id}", id)
                .then()
                .statusCode(204);

        given()
                .when().get("/api/bookings/{id}", id)
                .then()
                .statusCode(404);
    }

    @Test
    void shouldRejectInvalidPayload() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "passengerName", "",
                        "flightNumber", "GA-200",
                        "departureCity", "Jakarta",
                        "arrivalCity", "Denpasar",
                        "seatClass", "ECONOMY",
                        "price", BigDecimal.ZERO
                ))
                .when().post("/api/bookings")
                .then()
                .statusCode(400);
    }

    private Map<String, Object> validBookingPayload(String passengerName, String flightNumber) {
        return Map.of(
                "passengerName", passengerName,
                "flightNumber", flightNumber,
                "departureCity", "Jakarta",
                "arrivalCity", "Surabaya",
                "seatClass", "ECONOMY",
                "price", new BigDecimal("850000")
        );
    }
}
