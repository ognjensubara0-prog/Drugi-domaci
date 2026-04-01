package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/branch")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BranchResource {

    @Inject
    BranchService branchService;

    @POST
    @Path("/addBranch")
    public Response addBranch(Branch branch) {
        branchService.createBranch(branch);
        return Response.ok("Branch dodana uspješno!").build();
    }

    @GET
    @Path("/getAllBranches")
    public List<Branch> getAllBranches() {
        return branchService.getAllBranches();
    }
}