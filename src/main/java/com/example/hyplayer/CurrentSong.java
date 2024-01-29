package com.example.hyplayer;

public class CurrentSong {
    private static CurrentSong instance;
    private int songId;
    private CurrentSong() {
    }
    public static synchronized CurrentSong getInstance() {
        if (instance == null) {
            instance = new CurrentSong();
        }
        return instance;
    }
    public int getSongId() {
        return songId;
    }
    public void setSongId(int songId) {
        this.songId = songId;
    }
}
