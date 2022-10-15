package com.example.musicplayer;

public class Song {
    private String mnameofSong;
    private String msrcofSong;
    private String mpath;
    public boolean mstateofSong; //playing or not playing
    public Song(String nameofSong,String srcofSong,String path,boolean stateofSong){
        mnameofSong=nameofSong;
        msrcofSong=srcofSong;
        mpath=path;
        mstateofSong=stateofSong;
    }
    public String getMnameofSong(){return mnameofSong;}
    public String getMsrcofSong(){return msrcofSong;}

    public String getMpath() {
        return mpath;
    }
    public boolean getMstateofSong(){return mstateofSong;}
}
