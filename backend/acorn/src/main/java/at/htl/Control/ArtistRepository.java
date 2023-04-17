package at.htl.Control;

import at.htl.Entity.Artist;
import at.htl.Entity.Dto.ArtistDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class ArtistRepository implements PanacheRepository<Artist> {

    @Override
    public void persist(Artist artist) {
        if(this.find("name",artist.getName()).count() == 0)
            PanacheRepository.super.persist(artist);
    }

    public List<Artist> getMany(List<ArtistDTO> dtos){
        List<Artist> artists = new LinkedList<>();

        for (ArtistDTO dto:dtos) {
            artists.add(this.find("name", dto.getName()).firstResult());
        }

        return artists;
    }
}
