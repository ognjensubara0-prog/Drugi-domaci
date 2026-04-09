package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

   @POST
    @Path("/add")
    @RolesAllowed("admin")
    public Response addUser(User user) {
        User created = userService.addUser(user);
        return Response.ok().entity(created).build();
    }

    @GET
    @Path("/all")
    public Response getAll() {
        List<User> users = userService.getAllUsers();
        return Response.ok().entity(users).build();
    }

    
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(user).build();
    }

   
    @GET
    @Path("/search")
    public Response search(@QueryParam("name") String name) {
        List<User> users = userService.findByName(name);
        return Response.ok().entity(users).build();
    }

    
    @GET
    @Path("/{id}/reservations")
    public Response getReservations(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
       
        return Response.ok().entity(user.getReservations()).build();
    }
}