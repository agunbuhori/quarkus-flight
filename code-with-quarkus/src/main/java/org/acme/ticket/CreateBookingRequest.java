package org.acme.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingRequest {
        @NotBlank
        private String passengerName;

        @NotBlank
        private String flightNumber;

        @NotBlank
        private String departureCity;

        @NotBlank
        private String arrivalCity;

        @NotBlank
        private String seatClass;

        @NotNull
        @Positive
        private BigDecimal price;
}
