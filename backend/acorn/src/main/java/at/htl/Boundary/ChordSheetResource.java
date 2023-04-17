package at.htl.Boundary;

import at.htl.Control.*;
import at.htl.Entity.*;
import at.htl.Entity.Dto.*;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/chord-sheet")
public class ChordSheetResource {

    @Inject
    ChordSheetRepository csRepo;
    @Inject
    SongRepository songRepo;
    @Inject
    ArtistRepository artistRepo;

    @Inject
    Logger log;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        log.info(csRepo.findAll());
        return Response.ok(csRepo.findAll().stream().toList()).build();
    }

    @GET
    @Path("/for")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOfSong(@QueryParam("name") String name){
        return Response.ok(csRepo.findFor(name)).build();
    }

    @GET
    @Path("/for-id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOfSheetsForId(@QueryParam("id") Long id){
        return Response.ok(csRepo.findWithSongId(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addChordSheet(ChordSheetDTO chordSheet){
        SongDTO songDto = chordSheet.getSong();
        String chords = "";
        artistRepo.persist(new Artist(songDto.getArtists().getName()));

        if(songRepo.findByArtistAndName(songDto.getArtists().getName(), songDto.getName()) == null) {

            log.info("new Song");
            songRepo.persist(new Song(
                    songDto.getName(),
                    artistRepo.find("name", songDto.getArtists().getName()).firstResult(),
                    (chordSheet.getType() == Type.CHORDS)
                            ? csRepo.onlyGetLyrics(chordSheet.getSheet(), "%")
                            : csRepo.onlyGetLyrics(chordSheet.getSheet(), "|"),
                    songDto.getAvgDuration()));
        }

        chords = csRepo.getAllChords(chordSheet.getSheet());

        csRepo.persist(new ChordSheet(
                chordSheet.getSheet().trim(),
                songRepo.find("name", songDto.getName()).firstResult(),
                chords,
                chordSheet.getType()
        ));
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") Long id){
        csRepo.delete("id", id);
        return Response.ok().build();
    }

}
