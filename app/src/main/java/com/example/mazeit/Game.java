package com.example.mazeit;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Game {
    public String key;
    public String code;
    public String password;
    public Game(){
    }
    public Game(String code, String password,String key){

        this.code = code;
        this.password = password;
        this.key = key;
    }

}
