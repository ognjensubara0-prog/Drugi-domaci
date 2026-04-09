package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserProfileResource {

    @Inject
    UserProfileService profileService;

    @POST
    @Path("/add")
    public Response addProfile(UserProfile profile) {
        UserProfile created = profileService.addProfile(profile);
        return Response.ok().entity(created).build();
    }

    @GET
    @Path("/all")
    public List<UserProfile> getAll() {
        return profileService.getAllProfiles();
    }

    
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        UserProfile profile = profileService.getProfileById(id);
        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(profile).build();
    }

    
    @GET
    @Path("/search")
    public Response searchByPhone(@QueryParam("phone") String phone) {
        List<UserProfile> profiles = profileService.findByPhone(phone);
        return Response.ok().entity(profiles).build();
    }
}