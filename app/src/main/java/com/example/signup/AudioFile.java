package com.example.signup;

import android.net.Uri;

import java.io.Serializable;

public class AudioFile implements Serializable {
    private String ID;
    // Müzik için metadatayı tutan class
    private String path;
    private String name;
    private long dur;
    private String artist;

    public AudioFile(String ID,String path,String name,String artist,long dur) {
        this.ID = ID;
        this.path = path;
        this.name = name;
        this.artist = artist;
        this.dur = dur;
    }

    public String getpath() {
        return path;
    }
    public void setapath(String path) {
        this.path = path;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public long getDur() {
        return dur;
    }

    public void setDur(long dur) {
        this.dur = dur;
    }
    public String getID() {
        return ID;
    }//Album kapagi cekmek icin

    public void setID(String ID) {
        this.ID = ID;
    }
}