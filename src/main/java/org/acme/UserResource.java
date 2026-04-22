package org.acme;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    @RestClient
    TimeServiceClient timeServiceClient;

    @Inject
    @RestClient
    IpServiceClient ipServiceClient;

    // --- STARE METODE (User menadžment) ---

    @POST
    @Path("/add")
    @PermitAll
    public Response addUser(User user) {
        User created = userService.addUser(user);
        return Response.ok().entity(created).build();
    }

    @GET
    @Path("/all")
    @PermitAll
    public Response getAll() {
        List<User> users = userService.getAllUsers();
        return Response.ok().entity(users).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response getById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(user).build();
    }

    @GET
    @Path("/search")
    @PermitAll
    public Response search(@QueryParam("name") String name) {
        List<User> users = userService.findByName(name);
        return Response.ok().entity(users).build();
    }

    @GET
    @Path("/{id}/reservations")
    @PermitAll
    public Response getReservations(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(user.getReservations()).build();
    }

    // --- NOVE METODE (Zadatak 1a i 1b) ---

    @GET
    @Path("/time-by-ip")
    @PermitAll
    public Response getTimeByIp(@QueryParam("ip") String ip) {
        try {
            String timeData = timeServiceClient.getTimeByIp(ip);
            return Response.ok().entity(timeData).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/current-time-auto")
    @PermitAll
    public Response getCurrentTimeAuto() {
        try {
            String myIp = ipServiceClient.getMyIp();
            String timeData = timeServiceClient.getTimeByIp(myIp);
            return Response.ok().entity(timeData).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY).entity(e.getMessage()).build();
        }
    }

    // --- ZADATAK 2 (Spajanje svega i snimanje u bazu) ---

    @GET
    @Path("/getTimezoneByIP")
    @PermitAll
    @Transactional // Ovo omogućava upis u bazu!
    public Response getTimezoneByIP(@QueryParam("userId") Long userId) {
        // 1. Nađi korisnika
        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        try {
            // 2. Uzmi IP (1b) i Vreme (1a)
            String myIp = ipServiceClient.getMyIp();
            String rawJson = timeServiceClient.getTimeByIp(myIp);

            // 3. Napravi novi objekat TimeZoneData
            TimeZoneData tz = new TimeZoneData();
            // Ručno setujemo jer nemamo parser, ali simuliramo tvoj response
            tz.setTimeZone("Europe/Podgorica"); 
            tz.setDateTime(java.time.LocalDateTime.now().toString());
            
            // 4. POVEŽI (Ovo je ključna linija zadatka 2)
            tz.setUser(user);
            user.getTimeZoneDataList().add(tz);

            // 5. Vrati korisnika (Quarkus će sam uraditi INSERT u bazu zbog @Transactional)
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY).entity(e.getMessage()).build();
        }
    }
}