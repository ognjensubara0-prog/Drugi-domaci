package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    @POST
    @Path("/add")
    public Response addPayment(Payment payment) {
        Payment created = paymentService.addPayment(payment);
        return Response.ok().entity(created).build();
    }

    @GET
    @Path("/all")
    public List<Payment> getAll() {
        return paymentService.getAllPayments();
    }

    
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(payment).build();
    }

    
    @GET
    @Path("/search")
    public Response searchByMethod(@QueryParam("method") String method) {
        List<Payment> payments = paymentService.findByMethod(method);
        return Response.ok().entity(payments).build();
    }
}