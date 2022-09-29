package com.example.mazeit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

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

public class RecList_activity extends AppCompatActivity {
    ListView recL;
    RecordList list = new RecordList();
    DatabaseReference Ref;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_list);

        recL=(ListView)findViewById(R.id.recList);
        firebaseDatabase = FirebaseDatabase.getInstance("https://mazeit-5ca2d-default-rtdb.europe-west1.firebasedatabase.app/");
        Ref = firebaseDatabase.getReference("record").child("-N2mbGLyLvWUTvF6b-DI");

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

    }
}