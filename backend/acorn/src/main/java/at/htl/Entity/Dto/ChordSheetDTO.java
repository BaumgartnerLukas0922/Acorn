package at.htl.Entity.Dto;

import at.htl.Entity.Type;

public class ChordSheetDTO {
    private String sheet;
    private SongDTO song;
    private Type type;

    public ChordSheetDTO(String sheet, SongDTO song, Type type) {
        this.sheet = sheet;
        this.song = song;
        this.type = type;
    }

    public ChordSheetDTO() {
    }

    public SongDTO getSong() {
        return song;
    }

    public void setSong(SongDTO song) {
        this.song = song;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Chord Sheet for Song: "+ song.getName();
    }

}
