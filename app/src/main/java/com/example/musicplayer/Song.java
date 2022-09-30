package com.example.musicplayer;

public class Song {
    private String mnameofSong;
    private String msrcofSong;
    private String mpath;
    public Song(String nameofSong,String srcofSong,String path){
        mnameofSong=nameofSong;
        msrcofSong=srcofSong;
        mpath=path;
    }
    public String getMnameofSong(){return mnameofSong;}
    public String getMsrcofSong(){return msrcofSong;}

    public String getMpath() {
        return mpath;
    }
}
