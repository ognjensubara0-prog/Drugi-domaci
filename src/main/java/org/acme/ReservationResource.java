package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/reservation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {

    @Inject
    ReservationService reservationService;

    @POST
    @Path("/add")
    public Response addReservation(Reservation reservation) {
        Reservation created = reservationService.addReservation(reservation);
        return Response.ok().entity(created).build();
    }

    @GET
    @Path("/all")
    public List<Reservation> getAll() {
        return reservationService.getAllReservations();
    }

    
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Reservation res = reservationService.getReservationById(id);
        if (res == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(res).build();
    }

    
    @GET
    @Path("/search")
    public Response searchByStatus(@QueryParam("status") String status) {
        List<Reservation> reservations = reservationService.findByStatus(status);
        return Response.ok().entity(reservations).build();
    }
}