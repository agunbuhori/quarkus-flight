package org.acme.ticket;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class FlightBookingService {

    private final AtomicLong idSequence = new AtomicLong(0L);
    private final Map<Long, FlightBooking> bookings = new ConcurrentHashMap<>();

    public FlightBooking create(CreateBookingRequest request) {
        long id = idSequence.incrementAndGet();

        FlightBooking booking = new FlightBooking(
                id,
            request.getPassengerName().trim(),
            request.getFlightNumber().trim(),
            request.getDepartureCity().trim(),
            request.getArrivalCity().trim(),
            request.getSeatClass().trim(),
            request.getPrice(),
                "CONFIRMED"
        );

        bookings.put(id, booking);
        return booking;
    }

    public List<FlightBooking> findAll() {
        List<FlightBooking> result = new ArrayList<>(bookings.values());
        result.sort(Comparator.comparingLong(FlightBooking::id));
        return result;
    }

    public FlightBooking findById(long id) {
        return bookings.get(id);
    }

    public boolean delete(long id) {
        return bookings.remove(id) != null;
    }
}
