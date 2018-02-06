package by.kazimirov.entity;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 */
public class Track implements IDatabaseEntity {
    private int trackId;
    private int albumId;
    private int genreId;
    private String title;
    private double price;
    private int discount;
    private String location;
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return trackId == track.trackId &&
                albumId == track.albumId &&
                genreId == track.genreId &&
                Double.compare(track.price, price) == 0 &&
                discount == track.discount &&
                Objects.equals(title, track.title) &&
                Objects.equals(location, track.location) &&
                Arrays.equals(image, track.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, albumId, genreId, title, price, discount, location, image);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Track{");
        sb.append("trackId=").append(trackId);
        sb.append(", albumId=").append(albumId);
        sb.append(", genreId=").append(genreId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", price=").append(price);
        sb.append(", discount=").append(discount);
        sb.append(", location='").append(location).append('\'');
        sb.append(", image=").append(Arrays.toString(image));
        sb.append('}');
        return sb.toString();
    }

    public Track(){}

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
