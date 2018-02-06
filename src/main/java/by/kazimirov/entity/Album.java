package by.kazimirov.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 *
 */
public class Album implements IDatabaseEntity {
    private int albumId;
    private int artistId;
    private String title;
    private Date releaseDate;
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return albumId == album.albumId &&
                artistId == album.artistId &&
                Objects.equals(title, album.title) &&
                Objects.equals(releaseDate, album.releaseDate) &&
                Arrays.equals(image, album.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, artistId, title, releaseDate, image);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Album{");
        sb.append("albumId=").append(albumId);
        sb.append(", artistId=").append(artistId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", image=").append(Arrays.toString(image));
        sb.append('}');
        return sb.toString();
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
