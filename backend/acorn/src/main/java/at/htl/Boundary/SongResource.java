package at.htl.Boundary;

import at.htl.Control.ArtistRepository;
import at.htl.Control.SongRepository;
import at.htl.Entity.Artist;
import at.htl.Entity.Dto.SongDTO;
import at.htl.Entity.Song;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/song")
public class SongResource {

    @Inject
    SongRepository songRepo;

    @Inject
    ArtistRepository artistRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        return Response.ok(songRepo.findAll().stream().toList()).build();
    }

    @GET
    @Path("/text")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getText(@QueryParam("artistName")String artistName, @QueryParam("songName") String songName){
        return Response.ok(songRepo.findByArtistAndName(artistName, songName)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response add(SongDTO song){
        artistRepository.persist(new Artist(song.getArtists().getName()));
        songRepo.persist(
                new Song(
                        song.getName(),
                        artistRepository.find("name", song.getArtists().getName()).firstResult(),
                        song.getRawLyrics(),
                        song.getAvgDuration()));
        return Response.ok().build();
    }

    @GET
    @Path("/for")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOfSong(@QueryParam("name") String name){
        return Response.ok(songRepo.findFor(name)).build();
    }

}
