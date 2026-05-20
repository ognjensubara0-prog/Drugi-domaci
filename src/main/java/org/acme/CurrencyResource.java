package org.acme;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/currency")
public class CurrencyResource {

    @Inject
    CurrencyManager currencyManager; 

    @GET
    @Path("/currencyConversion")
    //@RolesAllowed("admin") 
    @Produces(MediaType.APPLICATION_JSON)
    public Response convertCurrency(
            @QueryParam("from") String from,
            @QueryParam("to") String to,
            @QueryParam("value") double value,
            @QueryParam("userId") Long userId) {

        try {
           
            CurrencyResponse result = currencyManager.processCurrencyRequest(from, to, value, userId);
            
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Greška prilikom konverzije: " + e.getMessage())
                           .build();
        }
    }
}

