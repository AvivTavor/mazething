package com.example.mazeit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Ref;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private void sendMessage(String massage)
    {


        Intent intent
                = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");

        intent.putExtra(
                Intent.EXTRA_TEXT,
                massage);

        startActivity(intent);
    }
    public static String randomid()
    {
        String text = "1234567890-_qwertyuiop[]asdfghjkl;'zxcvbnm,./QWERTYUIOPASDFGHJKLZXCVBNM<>";
        Random rng = new Random();
        String c="";
        int index;
        for(int i=0;i<=10;i++) {
            index = rng.nextInt(text.length());
            c+=text.charAt(index);
        }
        return c;
    }
    Button mazetest;
    Button online;
    SharedPreferences sp;
    Button finalmaze;
    Button showRecs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mazetest = findViewById(R.id.trything);
        online = findViewById(R.id.online);
        showRecs=findViewById(R.id.showRec);
        sp=getSharedPreferences("scores",0);
        mazetest.setOnClickListener(this);
        online.setOnClickListener(this);
        showRecs.setOnClickListener(this);
        finalmaze = findViewById(R.id.finalmaze);

        finalmaze.setOnClickListener(this);

        SharedPreferences.Editor editor=sp.edit();

        if(sp.getString("userid", "").equals("")){
            editor.putString("userid",randomid());
            editor.putString("name","Guest "+sp.getString("userid",""));
            editor.putBoolean("need3",false);
            editor.putBoolean("need4",false);
            editor.putBoolean("need5",false);
            editor.putBoolean("need6",false);
            editor.putBoolean("need7",false);
            editor.putBoolean("need8",false);
            editor.putBoolean("need9",false);
            editor.putBoolean("need10",false);
        }
        editor.putBoolean("coded",true);
        editor.putInt("1star3",1200);
        editor.putInt("1star4",1900);
        editor.putInt("1star5",2550);
        editor.putInt("1star6",3500);
        editor.putInt("1star7",4000);
        editor.putInt("1star8",5250);
        editor.putInt("1star9",6700);
        editor.putInt("1star10",8000);
        editor.putInt("2star3",900);
        editor.putInt("2star4",1350);
        editor.putInt("2star5",1800);
        editor.putInt("2star6",2300);
        editor.putInt("2star7",2800);
        editor.putInt("2star8",3500);
        editor.putInt("2star9",4300);
        editor.putInt("2star10",5000);
        editor.putInt("3star3",600);
        editor.putInt("3star4",950);
        editor.putInt("3star5",1300);
        editor.putInt("3star6",1750);
        editor.putInt("3star7",2150);
        editor.putInt("3star8",2550);
        editor.putInt("3star9",2900);
        editor.putInt("3star10",3350);
        editor.commit();

    }

    @Override
    public void onClick(View v) {

        if(v==finalmaze){
            Intent intent=new Intent(this,levelmenu.class);

            startActivity(intent);
        }
        if(v==mazetest){
            //Intent intent=new Intent(this,MazeTest.class);

            //startActivity(intent);

            sendMessage("Hello dear friends! I just found this really cool game, it's called MazeIt. go check it out!");
        }
        if(v==online){
            Intent intent=new Intent(this,testthing.class);

            startActivity(intent);
        }
        if(v==showRecs){
            Intent intent=new Intent(this,RecList_activity.class);

            startActivity(intent);
        }

    }
}