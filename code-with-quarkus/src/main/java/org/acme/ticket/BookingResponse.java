package org.acme.ticket;

import java.math.BigDecimal;

public record BookingResponse(
        long id,
        String passengerName,
        String flightNumber,
        String departureCity,
        String arrivalCity,
        String seatClass,
        BigDecimal price,
        String status
) {
}
