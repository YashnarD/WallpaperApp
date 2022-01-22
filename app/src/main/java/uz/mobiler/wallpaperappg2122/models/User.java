package uz.mobiler.wallpaperappg2122.models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    public String id;
    public Date updated_at;
    public String username;
    public String name;
    public String first_name;
    public String last_name;
    public String twitter_username;
    public String portfolio_url;
    public String bio;
    public String location;
    public Links links;
    public ProfileImage profile_image;
    public String instagram_username;
    public int total_collections;
    public int total_likes;
    public int total_photos;
    public boolean accepted_tos;
    public boolean for_hire;
    public Social social;
}
