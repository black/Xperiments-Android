package com.example.musicplayer;

public class SongData {
    private String title;
    private String artist;
    private String uri;
    private String img;

    public SongData(String title, String artist,String uri,String img) {
        this.title = title;
        this.artist = artist;
        this.uri = uri;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getUri() {
        return uri;
    }
    public String getImg() {
        return img;
    }
}
