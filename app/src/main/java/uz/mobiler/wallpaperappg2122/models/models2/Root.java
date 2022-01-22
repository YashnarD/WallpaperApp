package uz.mobiler.wallpaperappg2122.models.models2;

import java.util.Date;
import java.util.List;

public class Root{
    public String id;
    public Date created_at;
    public Date updated_at;
    public Date promoted_at;
    public int width;
    public int height;
    public String color;
    public String blur_hash;
    public String description;
    public String alt_description;
    public Urls urls;
    public Links links;
    public List<Object> categories;
    public int likes;
    public boolean liked_by_user;
    public List<Object> current_user_collections;
    public Object sponsorship;
    public TopicSubmissions topic_submissions;
    public User user;
    public Exif exif;
    public Location location;
    public int views;
    public int downloads;
}
