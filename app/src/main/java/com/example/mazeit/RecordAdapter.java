package com.example.mazeit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {

    Context context;


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
    List<Record> objects;
    public RecordAdapter(Context context, int resource, int textViewResourceId, List<Record> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.record_list_adapter,parent,false);

        TextView name = (TextView)view.findViewById(R.id.name);
        TextView time = (TextView)view.findViewById(R.id.time);
        Record temp = objects.get(position);

        name.setText(temp.nickname);
        time.setText(totext(temp.time));

        return view;
    }
}
