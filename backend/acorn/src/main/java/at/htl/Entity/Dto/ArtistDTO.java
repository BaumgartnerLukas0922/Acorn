package at.htl.Entity.Dto;

public class ArtistDTO {
    private String name;

    public ArtistDTO(String name) {
        this.name = name;
    }

    public ArtistDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
