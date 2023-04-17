package at.htl.Control;

import at.htl.Entity.Artist;
import at.htl.Entity.Dto.SongDTO;
import at.htl.Entity.Song;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class SongRepository implements PanacheRepository<Song> {

    @Inject
    Logger log;

    public Song findByArtistAndName(String artist, String name){
        List<Song> all = this.findAll().stream().toList();

        for (Song song:all) {
            log.info(song.getName()+"="+name);
            log.info(song.getArtists().getName()+"="+artist);

            if(song.getArtists().getName().toLowerCase().equals(artist.toLowerCase()) &&
                    song.getName().toLowerCase().equals(name.toLowerCase())){
                return song;
            }
        }
        return null;
    }

    public List<Song> findFor(String name) {
        List<Song> all = this.findAll().stream().toList();
        List<Song> selected = new LinkedList<Song>();

        for (Song song:all) {
            if(song.getArtists().getName().equalsIgnoreCase(name)
            || song.getName().equalsIgnoreCase(name)){
                selected.add(song);
            }
        }

        return selected;
    }
}
