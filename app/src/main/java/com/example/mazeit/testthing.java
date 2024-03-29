package com.example.mazeit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class testthing extends AppCompatActivity implements View.OnClickListener {
    EditText pass;
    TextView showkey;
    EditText code;
    Button create;
    int codestr;
    String passstr;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference gameRef;
    SharedPreferences sp;
    DatabaseReference[] RecRef = new DatabaseReference[10];
    String[] keys = {"-N2mbGLyLvWUTvF6b-DI","-N2mbGM4FBgBFIeg-hp-","-N2mbGM5GFwxXNH3wJI1","-N2mbGM6L3Y7hhQeVOth","-N2mbGM7KitQ6d1AZhMq","-N2mbGM8yoBClOw6SkEj","-N2mbGM9rEZ7__NZnWn9","-N2mbGMADsp21vMmCwbL","-N2mbGMB1NQfvbCfm1pj","-N2mbGMCCZgd14S0wkTx"};
    RecordList recL = new RecordList();
    RecordList examplerec = new RecordList(new Record());


    public void uploadRecord(Record record, DatabaseReference Ref){
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RecordList recL = new RecordList();
                recL = snapshot.getValue(recL.getClass());
                assert recL != null;
                Ref.setValue(recL.getNext());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(
                        testthing.this,
                        "upload failed, please try again later.",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }
    public static String totext(long n){
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

        return s;
    }
    RecordList[] recLists = new RecordList[10];
    public String emptiness(RecordList r){
        if(r==null){
            return "it's null";
        }
        else if(r.value==null){
            return "it's empty inside";
        }
        else{
            return "it's a legitimate list";
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testthing);
        sp =getSharedPreferences("scores",0);
        pass = findViewById(R.id.pass);
        code = findViewById(R.id.code);
        create = findViewById(R.id.create);


        create.setOnClickListener(this);
        showkey = findViewById(R.id.showthingy);
        showkey.setText(sp.getString("userid",""));
        firebaseDatabase = FirebaseDatabase.getInstance("https://mazeit-5ca2d-default-rtdb.europe-west1.firebasedatabase.app/");

    }

    @Override
    public void onClick(View view) {
        if(view==create) {
            gameRef = firebaseDatabase.getReference("record").child(keys[9]);
            uploadRecord(new Record(8, 0, null, null), gameRef);
        };/*

            gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    recL = snapshot.getValue(recL.getClass());
                    assert recL != null;
                    recL = recL.update(r);
                    gameRef.setValue(recL);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    showkey.setText("function failed");
                }

            });*/
        }
    }
