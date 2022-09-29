package com.example.mazeit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {

    Context context;

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
        time.setText(String.valueOf(temp.time));

        return view;
    }
}
