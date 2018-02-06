package by.kazimirov.entity;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 */
public class Artist implements IDatabaseEntity {
    private int artistId;
    private String name;
    private String country;
    private String description;
    private byte[] image;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return artistId == artist.artistId &&
                Objects.equals(name, artist.name) &&
                Objects.equals(country, artist.country) &&
                Objects.equals(description, artist.description) &&
                Arrays.equals(image, artist.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, name, country, description, image);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Artist{");
        sb.append("artistId=").append(artistId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", image=").append(Arrays.toString(image));
        sb.append('}');
        return sb.toString();
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
