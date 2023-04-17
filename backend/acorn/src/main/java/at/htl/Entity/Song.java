package at.htl.Entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="A_SONG")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="S_ID")
    private Long Id;

    @Column(name="S_NAME")
    private String name;

    @JoinColumn(name="S_ARTIST")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Artist artists;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name="S_RAW_LYRICS")
    private String rawLyrics;

    @Column(name="S_AVG_DURATION")
    private int avgDuration;

    public Song(String name, Artist artists, String rawLyrics, int avgDuration) {
        this.name = name;
        this.artists = artists;
        this.rawLyrics = rawLyrics;
        this.avgDuration = avgDuration;
    }

    public Song() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtists() {
        return artists;
    }

    public void setArtists(Artist artists) {
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

    @Override
    public String toString() {
        return String.format("""
                Song name:          %s
                Artists:            %n
                Average duration:   %n
                """, name, artists.getName(), avgDuration);
    }
}
