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
        // Izmijenjeno da koristi novu metodu koja ujedno puni i @Transient 'file' varijablu iz Stavke 3
        Branch branch = branchService.getBranchWithFilesLoaded(id);
        if (branch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(branch).build();
    }

    @GET
    @Path("/search")
    public Response searchByCity(@QueryParam("city") String city) {
        List<Branch> branches = branchService.findByCity(city);
        return Response.ok().entity(branches).build();
    }

    @GET
    @Path("/{id}/reservations")
    public Response getReservations(@PathParam("id") Long id) {
        Branch branch = branchService.getBranchById(id);
        if (branch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(branch.getReservations()).build();
    }

    // ---- NOVI ENDPOINTI IZ ZADATKA ----

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@QueryParam("id") Long id, FileUploadForm form) {
        if (form == null || form.getFile() == null || form.getFileName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Zahtjev mora da sadrži fajl i ime fajla.").build();
        }

        try {
            Branch updatedBranch = branchService.uploadFileToBranch(id, form);
            if (updatedBranch == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Branch sa proslijeđenim ID-jem ne postoji.").build();
            }
            return Response.ok().entity(updatedBranch).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Greška na serveru: " + e.getMessage()).build();
        }
    }
}