package com.example.mazeit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class levelmenu extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sp;
    TextView starsnum;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn10;
    TextView star1;
    TextView star2;
    TextView star3;
    TextView usertime;
    Button playbtn;
    MediaPlayer mediaPlayer;
    MediaPlayer gotolevel;
    MediaPlayer bpress;

    int k=0;
    public void onBackPressed() {
        mediaPlayer.stop();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public static String totext(long n){
        if(n==999999999){
            return "no time yet";
        }
        else{
        String s = "";
        s=n%100+s;
        if(n%100<10){
            s= "0"+s;
        }
        n=n/100;
        s=":"+s;
        s=n%60+s;
        if(n%60<10){
            s= "0"+s;
        }
        n=n/60;
        s=":"+s;
        s=n+s;

        return s;}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmenu);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.levelmenumusic);
        bpress = MediaPlayer.create(getApplicationContext(), R.raw.blop);
        gotolevel = MediaPlayer.create(getApplicationContext(), R.raw.gamestart);        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        sp=getSharedPreferences("scores",0);
        mediaPlayer.setVolume((float)sp.getInt("musicvol",10)/10,(float)sp.getInt("musicvol",10)/10);
        bpress.setVolume((float)sp.getInt("sfxvol",10)/10,(float)sp.getInt("sfxvol",10)/10);
        gotolevel.setVolume((float)sp.getInt("sfxvol",10)/10,(float)sp.getInt("sfxvol",10)/10);
        int s=0;
        for(int i=3;i<=10;                i++){
            s+=sp.getInt("star"+i,0);
        }

        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn10 = (Button) findViewById(R.id.button10);
        star1 = (TextView) findViewById(R.id.star1time);
        star2 = (TextView) findViewById(R.id.star2time);
        star3 = (TextView) findViewById(R.id.star3time);
        usertime = (TextView)  findViewById(R.id.usertime);
        playbtn = (Button) findViewById(R.id.playbtn);
        playbtn.setOnClickListener(this);
        playbtn.setClickable(false);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        starsnum=(TextView) findViewById(R.id.starsnum);
        starsnum.setText(""+s);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        if (v ==playbtn&&k!=0){
            gotolevel.start();
            mediaPlayer.stop();
            Intent intent=new Intent(this,MazeGame.class);
            intent.putExtra("size",k);
            startActivity(intent);
        }
        else{
            if(v==btn3){
                bpress.start();
                k=3;/*
                btn3.setBackgroundColor(android.R.color.holo_green_light);
                btn4.setBackgroundColor(android.R.color.holo_purple);
                btn5.setBackgroundColor(android.R.color.holo_purple);
                btn6.setBackgroundColor(android.R.color.holo_purple);
                btn7.setBackgroundColor(android.R.color.holo_purple);
                btn8.setBackgroundColor(android.R.color.holo_purple);
                btn9.setBackgroundColor(android.R.color.holo_purple);
                btn10.setBackgroundColor(android.R.color.holo_purple);*/
            }
            if(v==btn4){

                bpress.start();
                k=4;
                /*
                btn4.setBackgroundColor(123);
                btn3.setBackgroundColor(200);
                btn5.setBackgroundColor(200);
                btn6.setBackgroundColor(200);
                btn7.setBackgroundColor(200);
                btn8.setBackgroundColor(200);
                btn9.setBackgroundColor(200);
                btn10.setBackgroundColor(200);*/
            }
            if(v==btn5){

                bpress.start();
                k=5;
                /*
                btn5.setBackgroundColor(123);
                btn4.setBackgroundColor(200);
                btn3.setBackgroundColor(200);
                btn6.setBackgroundColor(200);
                btn7.setBackgroundColor(200);
                btn8.setBackgroundColor(200);
                btn9.setBackgroundColor(200);
                btn10.setBackgroundColor(200);*/
            }
            if(v==btn6){

                bpress.start();
                k=6;
                /*
                btn6.setBackgroundColor(123);
                btn4.setBackgroundColor(200);
                btn5.setBackgroundColor(200);
                btn3.setBackgroundColor(200);
                btn7.setBackgroundColor(200);
                btn8.setBackgroundColor(200);
                btn9.setBackgroundColor(200);
                btn10.setBackgroundColor(200);*/
            }
            if(v==btn7){

                bpress.start();
                k=7;
                /*
                btn7.setBackgroundColor(123);
                btn4.setBackgroundColor(200);
                btn5.setBackgroundColor(200);
                btn6.setBackgroundColor(200);
                btn3.setBackgroundColor(200);
                btn8.setBackgroundColor(200);
                btn9.setBackgroundColor(200);
                btn10.setBackgroundColor(200);*/
            }
            if(v==btn8){

                bpress.start();
                k=8;
                /*
                btn8.setBackgroundColor(123);
                btn4.setBackgroundColor(200);
                btn5.setBackgroundColor(200);
                btn6.setBackgroundColor(200);
                btn7.setBackgroundColor(200);
                btn3.setBackgroundColor(200);
                btn9.setBackgroundColor(200);
                btn10.setBackgroundColor(200);*/
            }
            if(v==btn9) {

                bpress.start();
                k = 9;
                /*
                btn9.setBackgroundColor(123);
                btn4.setBackgroundColor(200);
                btn5.setBackgroundColor(200);
                btn6.setBackgroundColor(200);
                btn7.setBackgroundColor(200);
                btn8.setBackgroundColor(200);
                btn3.setBackgroundColor(200);
                btn10.setBackgroundColor(200);*/
            }
            if(v==btn10){

                bpress.start();
                k=10;
                /*
                btn10.setBackgroundColor(123);
                btn4.setBackgroundColor(200);
                btn5.setBackgroundColor(200);
                btn6.setBackgroundColor(200);
                btn7.setBackgroundColor(200);
                btn8.setBackgroundColor(200);
                btn9.setBackgroundColor(200);
                btn3.setBackgroundColor(200);*/
            }

            star1.setText(totext(sp.getInt("1star"+k,0)));
            star2.setText(totext(sp.getInt("2star"+k,0)));
            star3.setText(totext(sp.getInt("3star"+k,0)));
            usertime.setText(totext(sp.getInt(""+k,0)));
            playbtn.setClickable(true);
        }
    }
}