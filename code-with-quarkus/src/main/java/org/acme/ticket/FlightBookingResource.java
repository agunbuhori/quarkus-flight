package org.acme.ticket;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlightBookingResource {

    @Inject
    FlightBookingService service;

    @POST
    public Response create(@Valid CreateBookingRequest request) {
        FlightBooking booking = service.create(request);
        return Response.status(Response.Status.CREATED).entity(booking.toResponse()).build();
    }

    @GET
    public List<BookingResponse> findAll() {
        return service.findAll().stream().map(FlightBooking::toResponse).toList();
    }

    @GET
    @Path("/{id}")
    public BookingResponse findById(@PathParam("id") long id) {
        FlightBooking booking = service.findById(id);
        if (booking == null) {
            throw new WebApplicationException("Booking not found", Response.Status.NOT_FOUND);
        }
        return booking.toResponse();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            throw new WebApplicationException("Booking not found", Response.Status.NOT_FOUND);
        }
        return Response.noContent().build();
    }
}
