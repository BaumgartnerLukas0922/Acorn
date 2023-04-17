package at.htl.Control;

import at.htl.Entity.ChordSheet;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ChordSheetRepository implements PanacheRepository<ChordSheet> {
    public List<ChordSheet> findFor(String name) {
        List<ChordSheet> all = this.findAll().stream().toList();
        List<ChordSheet> selected = new LinkedList<ChordSheet>();

        for (ChordSheet cs:all) {
            if(cs.getSong().getName().toLowerCase().equals(name.toLowerCase())
            || cs.getSong().getArtists().getName().toLowerCase().equals(name.toLowerCase())){
                selected.add(cs);
            }
        }

        return selected;
    }

    public List<ChordSheet> findWithSongId(Long id) {
        List<ChordSheet> all = this.findAll().stream().toList();
        List<ChordSheet> selected = new LinkedList<ChordSheet>();

        for (ChordSheet cs:all) {
            if(Objects.equals(cs.getSong().getId(), id)){
                selected.add(cs);
            }
        }

        return selected;
    }

    public String getAllChords(String sheet) {

        String[] lines = sheet.split("\n");
        String chordsAsString = "";

        List<String> chordLines = Arrays.stream(lines).toList().stream().filter(l -> l.startsWith("%")).toList();
        List<String> chords = new LinkedList<>();

        for (String line:chordLines) {
            for (String word:line.split("%| ")) {
                if(!word.trim().isEmpty()){
                    chords.add(word);
                }
            }
        }

        List<String> uniqueChords = chords.stream().distinct().toList();

        for (String c:uniqueChords) {
            if(!c.equals("N.C."))
                chordsAsString += c + ",";
        }
        if(chordsAsString.length() == 0)
            return "";
        return chordsAsString.substring(0, chordsAsString.length()-1);
    }

    public String onlyGetLyrics(String sheet, String splitter) {
        String[] lines = sheet.split("\n");
        List<String> lyrics = Arrays.stream(lines).toList().stream().filter(l -> !l.startsWith(splitter)).toList();
        String fullLyrics = "";

        for (String line:lyrics) {
            fullLyrics += line + "\n";
        }

        return fullLyrics.substring(0, fullLyrics.length()-1);
    }

    public String removePercent(String sheet){
        String[] lines = sheet.split("\n");
        String fullLyrics = "";

        for (String line: lines) {
            if(line.startsWith("%")){
                fullLyrics += line.substring(1) + "\n";
            } else {
                fullLyrics += line +"\n";
            }
        }
        return fullLyrics;
    }
}
