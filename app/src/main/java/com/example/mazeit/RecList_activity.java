package com.example.mazeit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RecList_activity extends AppCompatActivity implements View.OnClickListener {



    SharedPreferences sp;
    ListView recL;
    Button bt3;
    Button bt4;
    Button bt5;
    Button bt6;
    Button bt7;
    Button bt8;
    Button bt9;
    Button bt10;
    String[] keys = {"-N2mbGLyLvWUTvF6b-DI","-N2mbGM4FBgBFIeg-hp-","-N2mbGM5GFwxXNH3wJI1","-N2mbGM6L3Y7hhQeVOth","-N2mbGM7KitQ6d1AZhMq","-N2mbGM8yoBClOw6SkEj","-N2mbGM9rEZ7__NZnWn9","-N2mbGMADsp21vMmCwbL","-N2mbGMB1NQfvbCfm1pj","-N2mbGMCCZgd14S0wkTx"};
    RecordList list = new RecordList();
    DatabaseReference Ref;
    DatabaseReference Ref3;
    DatabaseReference Ref4;
    DatabaseReference Ref5;
    DatabaseReference Ref6;
    DatabaseReference Ref7;
    DatabaseReference Ref8;
    DatabaseReference Ref9;
    DatabaseReference Ref10;
    FirebaseDatabase firebaseDatabase;
    public void showRecords(DatabaseReference R){
        R.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            list= snapshot.getValue(list.getClass());
            assert recL != null;
            recL.setAdapter(new RecordAdapter(RecList_activity.this,0,0,list.show_list()));
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }

    });}
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_list);

        sp=getSharedPreferences("scores",0);
        bt3 = findViewById(R.id.buttonR3);
        bt4 = findViewById(R.id.buttonR4);
        bt5 = findViewById(R.id.buttonR5);
        bt6 = findViewById(R.id.buttonR6);
        bt7 = findViewById(R.id.buttonR7);
        bt8 = findViewById(R.id.buttonR8);
        bt9 = findViewById(R.id.buttonR9);
        bt10 = findViewById(R.id.buttonR10);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);
        recL=(ListView)findViewById(R.id.recList);
        firebaseDatabase = FirebaseDatabase.getInstance("https://mazeit-5ca2d-default-rtdb.europe-west1.firebasedatabase.app/");

        Ref3 = firebaseDatabase.getReference("record").child(keys[2]);
        Ref4 = firebaseDatabase.getReference("record").child(keys[3]);
        Ref5 = firebaseDatabase.getReference("record").child(keys[4]);
        Ref6 = firebaseDatabase.getReference("record").child(keys[5]);
        Ref7 = firebaseDatabase.getReference("record").child(keys[6]);
        Ref8 = firebaseDatabase.getReference("record").child(keys[7]);
        Ref9 = firebaseDatabase.getReference("record").child(keys[8]);
        Ref10 = firebaseDatabase.getReference("record").child(keys[9]);


        /*Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list= snapshot.getValue(list.getClass());
                assert recL != null;
                recL.setAdapter(new RecordAdapter(RecList_activity.this,0,0,list.show_list()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
         */

    }

    @Override
    public void onClick(View view) {
        if(view == bt3){
        showRecords(Ref3);
        }

        if(view == bt4){
            showRecords(Ref4);}
        if(view == bt5){
            showRecords(Ref5);}
        if(view == bt6){
            showRecords(Ref6);}
        if(view == bt7){
            showRecords(Ref7);}
        if(view == bt8){
            showRecords(Ref8);}
        if(view == bt9){
            showRecords(Ref9);}
        if(view == bt10){
            showRecords(Ref10);}
    }
}