package at.htl.Entity.Dto;

import at.htl.Entity.Artist;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

public class SongDTO {
    private String name;
    private ArtistDTO artists;
    private String rawLyrics;
    private int avgDuration;

    public SongDTO(String name, ArtistDTO artists, String rawLyrics, int avgDuration) {
        this.name = name;
        this.artists = artists;
        this.rawLyrics = rawLyrics;
        this.avgDuration = avgDuration;
    }

    public SongDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArtistDTO getArtists() {
        return artists;
    }

    public void setArtists(ArtistDTO artists) {
        this.artists = artists;
    }

    public String getRawLyrics() {
        return rawLyrics;
    }

    public void setRawLyrics(String rawLyrics) {
        this.rawLyrics = rawLyrics;
    }

    public int getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(int avgDuration) {
        this.avgDuration = avgDuration;
    }
}
