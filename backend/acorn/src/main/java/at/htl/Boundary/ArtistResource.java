package at.htl.Boundary;

import at.htl.Control.ArtistRepository;
import at.htl.Entity.Artist;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/artist")
public class ArtistResource {

    @Inject
    ArtistRepository artistRepo;

    @Inject
    Logger log;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        log.info("Gettin it");
        return Response.ok(artistRepo.findAll().stream().toList()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response add(@Context UriInfo uriInfo, Artist artist){
        artistRepo.persist(artist);
        return Response.ok().build();
    }
}
