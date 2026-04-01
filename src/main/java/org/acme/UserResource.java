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

    // Putanja: POST http://localhost:8080/user/add
    @POST
    @Path("/add")
    public Response addUser(User user) {
        User created = userService.addUser(user);
        return Response.ok().entity(created).build();
    }

    // Putanja: GET http://localhost:8080/user/all
    @GET
    @Path("/all")
    public Response getAll() {
        List<User> users = userService.getAllUsers();
        return Response.ok().entity(users).build();
    }

    // Putanja: GET http://localhost:8080/user/1
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(user).build();
    }
}