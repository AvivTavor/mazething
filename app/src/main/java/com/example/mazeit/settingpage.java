package com.example.mazeit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
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
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
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


    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpage);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        musicSeekBar = findViewById(R.id.musicvolume);
        sfxSeekBar = findViewById(R.id.sfxsound);

        sp =getSharedPreferences("scores",0);

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

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editor.commit();
            }
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
                    uploadRecord(new Record(i, sp.getInt("" + i, 0), name, sp.getString("userid", "")), firebaseDatabase.getReference("record").child(keys[i - 1]));
                }
                editor.commit();
            }
        }
    }
}