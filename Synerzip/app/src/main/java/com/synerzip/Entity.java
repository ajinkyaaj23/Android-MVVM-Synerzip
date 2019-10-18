package com.synerzip;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@androidx.room.Entity(tableName = "entity_table")
public class Entity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String rights;
    private String price;
    private String image;
    private String artist;
    private String title;
    private String link;
    private String apiID;
    private String contentType;
    private String category;
    private String releaseDate;

    @Ignore
    public Entity() {
    }

    public Entity(int id, String name, String rights, String price, String image, String artist, String title, String link, String apiID, String contentType, String category, String releaseDate) {
        this.id = id;
        this.name = name;
        this.rights = rights;
        this.price = price;
        this.image = image;
        this.artist = artist;
        this.title = title;
        this.link = link;
        this.apiID = apiID;
        this.contentType = contentType;
        this.category = category;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getApiID() {
        return apiID;
    }

    public void setApiID(String apiID) {
        this.apiID = apiID;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
