package com.example.mazeit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public void onBackPressed() {
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        //Play again
        mediaPlayer.start();
        //do more stuff
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
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
    Button play;
    Button recs;
    ImageButton settings;
    ImageButton share;
    SharedPreferences sp;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Audiofile in raw folder
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mainmenu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        play = findViewById(R.id.finalmaze);
        recs = findViewById(R.id.online);

        settings=findViewById(R.id.settings);
        sp=getSharedPreferences("scores",0);

        mediaPlayer.setVolume((float)sp.getInt("musicvol",10)/10,(float)sp.getInt("musicvol",10)/10);

        play.setOnClickListener(this);
        recs.setOnClickListener(this);
        settings.setOnClickListener(this);
        share = findViewById(R.id.share);

        share.setOnClickListener(this);

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

            editor.putInt("3",999999999);

            editor.putInt("4",999999999);

            editor.putInt("5",999999999);

            editor.putInt("6",999999999);

            editor.putInt("7",999999999);

            editor.putInt("8",999999999);

            editor.putInt("9",999999999);

            editor.putInt("10",999999999);

            editor.putString("name","Guest");

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

        if(v==play){

            mediaPlayer.stop();
            Intent intent=new Intent(this,levelmenu.class);

            startActivity(intent);

        }
        if(v==share){
            //Intent intent=new Intent(this,MazeTest.class);

            //startActivity(intent);

            sendMessage("Hello friends! I just found this really cool game, it's called MazeIt. go check it out!");
        }
        if(v==settings){

            mediaPlayer.stop();
            Intent intent=new Intent(this,settingpage.class);

            startActivity(intent);
        }
        if(v==recs){

            mediaPlayer.stop();
            if(internetIsConnected()){
            Intent intent=new Intent(this,RecList_activity.class);

            startActivity(intent);}
            else{

                Toast.makeText(
                        this,
                        "Please check your internet connection",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }
}