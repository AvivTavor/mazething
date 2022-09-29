package com.example.mazeit;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Record {
    public int size;
    public int time;
    public String nickname;
    public String code;
    public Record(){

    }

    public Record( int size, int time, String nickname, String code){
        this.size = size;
        this.time = time;
        this.nickname = nickname;
        this.code = code;
    }
}
