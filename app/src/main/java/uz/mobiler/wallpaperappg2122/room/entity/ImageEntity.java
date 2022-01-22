package uz.mobiler.wallpaperappg2122.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ImageEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id = 0;
    String id1;
    String author;
    int likes;
    String size;
    String thumb;
    String full;
    String regular;
    boolean isHave;

    public ImageEntity(String id1, String author, int likes, String size, String thumb, String full, String regular, boolean isHave) {
        this.id1 = id1;
        this.author = author;
        this.likes = likes;
        this.size = size;
        this.thumb = thumb;
        this.full = full;
        this.regular = regular;
        this.isHave = isHave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public boolean isHave() {
        return isHave;
    }

    public void setHave(boolean have) {
        isHave = have;
    }
}
