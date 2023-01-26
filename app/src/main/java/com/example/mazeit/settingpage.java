package com.example.mazeit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settingpage extends AppCompatActivity implements View.OnClickListener {
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public void onBackPressed() {
        music.stop();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public int tocolor(String s) {
        if (s.equals("green")) {
            return Color.GREEN;
        }
        if (s.equals("blue")) {
            return Color.BLUE;
        }
        if (s.equals("red")) {
            return Color.RED;
        }
        if (s.equals("black")) {
            return Color.BLACK;
        }
        if (s.equals("white")) {
            return Color.WHITE;
        }
        if (s.equals("dark gray")) {
            return Color.DKGRAY;
        }
        if (s.equals("yellow")) {
            return Color.YELLOW;
        }
        if (s.equals("cyan")) {
            return Color.CYAN;
        } if (s.equals("magenta")) {
            return Color.MAGENTA;
        }
        if (s.equals("light gray")) {
            return Color.LTGRAY;
        }
        else{
            return 0;
        }
    }
    public void onPause() {
        super.onPause();
        music.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        //Play again
        music.start();
        //do more stuff
    }

    public void uploadRecord(Record record, DatabaseReference Ref)  {
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RecordList recL = new RecordList();
                recL = snapshot.getValue(recL.getClass());
                assert recL != null;
                recL = recL.update(record);
                Ref.setValue(recL);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(
                        settingpage.this,
                        "upload failed, please try again later.",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }
    String[] keys = {"-N2mbGLyLvWUTvF6b-DI","-N2mbGM4FBgBFIeg-hp-","-N2mbGM5GFwxXNH3wJI1","-N2mbGM6L3Y7hhQeVOth","-N2mbGM7KitQ6d1AZhMq","-N2mbGM8yoBClOw6SkEj","-N2mbGM9rEZ7__NZnWn9","-N2mbGMADsp21vMmCwbL","-N2mbGMB1NQfvbCfm1pj","-N2mbGMCCZgd14S0wkTx"};
    Button changename;
    EditText newname;
    String name;
    SharedPreferences sp;
    FirebaseDatabase firebaseDatabase;
    SeekBar musicSeekBar;
    SeekBar sfxSeekBar;
    Button finishcolor;
    Button mazecolor;
    Button playercolor;
    Button backgroundcolor;
    ImageView iv;
    ImageButton backbutton;

    MediaPlayer music;
    Maze M = new Maze(3);


    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpage);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(this);
        musicSeekBar = findViewById(R.id.musicvolume);
        sfxSeekBar = findViewById(R.id.sfxsound);
        finishcolor = findViewById(R.id.finishcolor);
        mazecolor = findViewById(R.id.mazecolor);
        backgroundcolor = findViewById(R.id.backgroundcolor);
        playercolor = findViewById(R.id.playercolor);
        finishcolor.setOnClickListener(this);
        mazecolor.setOnClickListener(this);
        playercolor.setOnClickListener(this);
        backgroundcolor.setOnClickListener(this);
        iv = findViewById(R.id.iv);
        sp =getSharedPreferences("scores",0);

        M.RandomMaze();
        music = MediaPlayer.create(getApplicationContext(),R.raw.levelmenumusic);
        music.setVolume((float)sp.getInt("musicvol",10)/10,(float)sp.getInt("musicvol",10)/10);
        music.setLooping(true);
        music.start();
        iv.setImageBitmap(M.DrawMaze(sp.getInt("backgroundcolor",Color.BLACK),sp.getInt("playercolor",Color.GREEN),sp.getInt("finishcolor",Color.RED),sp.getInt("mazecolor",Color.GREEN)));
        if(sp.getInt("musicvol",-1)!=-1){
            musicSeekBar.setProgress(sp.getInt("musicvol",-1));
        }
        if(sp.getInt("sfxvol",-1)!=-1){
            sfxSeekBar.setProgress(sp.getInt("sfxvol",-1));
        }

        changename = findViewById(R.id.changename);
        newname = findViewById(R.id.newname);
        firebaseDatabase = FirebaseDatabase.getInstance("https://mazeit-5ca2d-default-rtdb.europe-west1.firebasedatabase.app/");
        newname.setText(sp.getString("name",""));
        changename.setOnClickListener(this);
        editor=sp.edit();

        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editor.putInt("musicvol",i);
                music.setVolume((float)i/10,(float)i/10);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editor.commit();            }
        });
        sfxSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editor.putInt("sfxvol",i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editor.commit();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == backbutton){
            onBackPressed();
        }
        if(view==changename){
            name = newname.getText().toString();
            if(name.length()>10||name.length()==0){
                Toast.makeText(
                        settingpage.this,
                        "your name needs to be between 1 and 10 characters long",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else if(!internetIsConnected()){
                Toast.makeText(
                        settingpage.this,
                        "please check your internet connection and try again",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else{
                editor.putString("name",name);
                for(int i=3;i<11;i++) {
                    if(sp.getInt("" + i, 999999999)!=999999999){
                        uploadRecord(new Record(i, sp.getInt("" + i, 0), name, sp.getString("userid", "")), firebaseDatabase.getReference("record").child(keys[i - 1]));
                    }
                }
                editor.commit();
            }
        }
        if(view==finishcolor){
            PopupMenu popupMenu = new PopupMenu(settingpage.this, finishcolor);

            // Inflating popup menu from popup_menu.xml file
            popupMenu.getMenuInflater().inflate(R.menu.hot_color_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    // Toast message on menu item clicked
                    editor.putInt("finishcolor",tocolor(menuItem.getTitle().toString()));
                    editor.commit();
                    iv.setImageBitmap(M.DrawMaze(sp.getInt("backgroundcolor",Color.BLACK),sp.getInt("playercolor",Color.GREEN),sp.getInt("finishcolor",Color.RED),sp.getInt("mazecolor",Color.GREEN)));
                    return true;
                }
            });
            // Showing the popup menu
            popupMenu.show();
        }
        if(view==mazecolor){
            PopupMenu popupMenu = new PopupMenu(settingpage.this, mazecolor);

            // Inflating popup menu from popup_menu.xml file
            popupMenu.getMenuInflater().inflate(R.menu.cold_color_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    // Toast message on menu item clicked
                    editor.putInt("mazecolor",tocolor(menuItem.getTitle().toString()));
                    editor.commit();
                    iv.setImageBitmap(M.DrawMaze(sp.getInt("backgroundcolor",Color.BLACK),sp.getInt("playercolor",Color.GREEN),sp.getInt("finishcolor",Color.RED),sp.getInt("mazecolor",Color.GREEN)));
                    return true;
                }
            });
            // Showing the popup menu
            popupMenu.show();
        }
        if(view==playercolor){
            PopupMenu popupMenu = new PopupMenu(settingpage.this, playercolor);

            // Inflating popup menu from popup_menu.xml file
            popupMenu.getMenuInflater().inflate(R.menu.cold_color_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    // Toast message on menu item clicked
                    editor.putInt("playercolor",tocolor(menuItem.getTitle().toString()));
                    editor.commit();
                    iv.setImageBitmap(M.DrawMaze(sp.getInt("backgroundcolor",Color.BLACK),sp.getInt("playercolor",Color.GREEN),sp.getInt("finishcolor",Color.RED),sp.getInt("mazecolor",Color.GREEN)));
                    return true;
                }
            });
            // Showing the popup menu
            popupMenu.show();
        }
        if(view==backgroundcolor){
            PopupMenu popupMenu = new PopupMenu(settingpage.this, backgroundcolor);

            // Inflating popup menu from popup_menu.xml file
            popupMenu.getMenuInflater().inflate(R.menu.grayish_color_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    // Toast message on menu item clicked
                    editor.putInt("backgroundcolor",tocolor(menuItem.getTitle().toString()));
                    editor.commit();
                    iv.setImageBitmap(M.DrawMaze(sp.getInt("backgroundcolor",Color.BLACK),sp.getInt("playercolor",Color.GREEN),sp.getInt("finishcolor",Color.RED),sp.getInt("mazecolor",Color.GREEN)));
                    return true;
                }
            });
            // Showing the popup menu
            popupMenu.show();
        }


    }
}