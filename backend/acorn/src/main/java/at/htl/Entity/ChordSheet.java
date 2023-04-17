package at.htl.Entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.jboss.resteasy.spi.touri.MappedBy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="A_CHORD_SHEET")
public class ChordSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CS_ID")
    private Long Id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "CS_SHEET")
    private String sheet;
    @JoinColumn(name="CS_SONG")
    @ManyToOne(cascade = CascadeType.ALL)
    private Song song;

    @JoinColumn(name="CS_CONTAINED_CHORDS")
    private String chords;

    @Column(name = "cs_sheet_type")
    private at.htl.Entity.Type type;

    public ChordSheet(String sheet, Song song, String chords, at.htl.Entity.Type type) {
        this.sheet = sheet;
        this.song = song;
        this.chords = chords;
        this.type = type;
    }

    public ChordSheet() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getChords() {
        return chords;
    }

    public void setChords(String chords) {
        this.chords = chords;
    }

    public at.htl.Entity.Type getType() {
        return type;
    }

    public void setType(at.htl.Entity.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Chord Sheet for Song: "+song.getName();
    }
}
