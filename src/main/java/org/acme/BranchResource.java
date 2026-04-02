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
    @Path("/add")
    public Response addBranch(Branch branch) {
        Branch created = branchService.addBranch(branch);
        return Response.ok().entity(created).build();
    }

    @GET
    @Path("/all")
    public List<Branch> getAll() {
        return branchService.getAllBranches();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Branch branch = branchService.getBranchById(id);
        if (branch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(branch).build();
    }

    // USLOV 3: Pretraga preko @QueryParam (npr. /branch/search?city=Podgorica)
    @GET
    @Path("/search")
    public Response searchByCity(@QueryParam("city") String city) {
        List<Branch> branches = branchService.findByCity(city);
        return Response.ok().entity(branches).build();
    }

    // USLOV 5: Endpoint koji vraća kolekciju rezervacija za određenu poslovnicu
    @GET
    @Path("/{id}/reservations")
    public Response getReservations(@PathParam("id") Long id) {
        Branch branch = branchService.getBranchById(id);
        if (branch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(branch.getReservations()).build();
    }
}