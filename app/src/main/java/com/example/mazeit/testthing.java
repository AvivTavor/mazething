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

        Record r1= new Record(6,1600,"aviv","asdfghjk");
        Record r2= new Record(6,1620,"moshe","asdfgsdafhjk");
        Record r3= new Record(6,1630,"danny","asdfgewrghjk");
        Record r4= new Record(6,1640,"noa","asdfghjdfhk");
        Record r5= new Record(6,1650,"noga","asdfgthhjk");
        Record r6= new Record(6,1660,"aviv2","asdf4hdgghjk");
        Record r7= new Record(6,1670,"aviv5","asdfbfergghjk");
        Record r8= new Record(6,1680,"shlomi","asdverjfghjk");
        Record r9= new Record(6,1690,"ori","asdf6yjghjk");
        RecordList testlist = new RecordList(r1,new RecordList(r2,new RecordList(r3,new RecordList(r4,new RecordList(r5,new RecordList(r6,new RecordList(r7,new RecordList(r8,new RecordList(r9)))))))));

        create.setOnClickListener(this);
        showkey = findViewById(R.id.showthingy);
        showkey.setText(sp.getString("userid",""));
        firebaseDatabase = FirebaseDatabase.getInstance("https://mazeit-5ca2d-default-rtdb.europe-west1.firebasedatabase.app/");
        gameRef=firebaseDatabase.getReference("record").child(keys[0]);
        gameRef.setValue(testlist);
    }

    @Override
    public void onClick(View view) {
        if(view==create){
            int size = Integer.parseInt(String.valueOf(code.getText()));
            int value = Integer.parseInt(String.valueOf(pass.getText()));
            gameRef=firebaseDatabase.getReference("record").child(keys[size-1]);
            Record r = new Record(size,value,sp.getString("name",""),sp.getString("userid",""));

            gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    showkey.setText("function succeeded");
                    recL = snapshot.getValue(recL.getClass());
                    assert recL != null;
                    recL = recL.update(r);
                    gameRef.setValue(recL);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    showkey.setText("function failed");
                }

            });
        }
    }
}