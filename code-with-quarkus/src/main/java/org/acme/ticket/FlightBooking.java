package org.acme.ticket;

import java.math.BigDecimal;

public record FlightBooking(
        long id,
        String passengerName,
        String flightNumber,
        String departureCity,
        String arrivalCity,
        String seatClass,
        BigDecimal price,
        String status
) {
    public BookingResponse toResponse() {
        return new BookingResponse(id, passengerName, flightNumber, departureCity, arrivalCity, seatClass, price, status);
    }
}
