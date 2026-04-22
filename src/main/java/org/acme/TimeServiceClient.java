package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(baseUri = "https://timeapi.io/api/time/current")
public interface TimeServiceClient {

    @GET
    @Path("/ip")
    @Produces(MediaType.APPLICATION_JSON)
    String getTimeByIp(@QueryParam("ipAddress") String ipAddress);
}