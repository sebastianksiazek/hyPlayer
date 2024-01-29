package com.example.hyplayer;

public class CurrentUserData {
    private static CurrentUserData instance;
    private int userId;
    private CurrentUserData() {
    }
   public static synchronized CurrentUserData getInstance() {
        if (instance == null) {
            instance = new CurrentUserData();
        }
        return instance;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
