package com.example.mazeit;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@IgnoreExtraProperties
public class RecordList {

    public Record value;
    public RecordList next;
    public String key;
    public RecordList(){
    }

    @Override
    public String toString() {
        return "RecordList{" +
                "value=" + value +
                ", next=" + next +
                ", key='" + key + '\'' +
                '}';
    }
    public List<Record> show_list(){
        ArrayList<Record> l = new ArrayList<Record>();
        l.add(this.value);
        if(this.getNext()!=null){
            l.addAll(this.next.show_list());
        }
        return l;

    }

    public void setList(RecordList rList){
        this.next = rList.getNext();

        Log.d("survived","1"+this.toString());
        this.value=rList.getValue();

        Log.d("survived","2"+this.toString());
        this.key=rList.key;
        Log.d("survived","3"+this.toString());
    }
    public RecordList(Record value){
        this.next =null;
        this.value = value;
        key = "";
    }
    public RecordList(Record value, RecordList next){
        this.next =next;
        this.value = value;
        key = "";
    }
    public Record getValue() {
        return value;
    }
    public void setNext(RecordList next) {
        this.next = next;
    }
    public void setValue(Record value) {
        this.value = value;
    }
    public RecordList pushRecord1(Record r){
        RecordList e = this;
        if (e.value.code.equals(r.code)) {
            e = e.next;
        }
        else {
            RecordList f = e;
            while (f.next != null) {
                if (f.next.value.code.equals(r.code)) {
                    f.setNext(f.next.next);
                }
                f=f.next;
            }
        }

        if(e.value == null){
            RecordList p = new RecordList(r);
            return p;
        }
        if(r.time<e.value.time){
            RecordList p = new RecordList(r);
            p.setNext(e);
            return p;
        }
        else{
            RecordList p;
            p = e;
            while(p.next!=null){
                if(p.next.value.time>r.time){
                    RecordList q = new RecordList(r);
                    q.setNext(p.next);
                    p.next = q;
                    return e;
                }
                p=p.next;
            }
            p.setNext(new RecordList(r));
            return e;
        }
    }
    public RecordList pushRecord(Record r){
        if(this.value == null){
            RecordList p = new RecordList(r);
            return p;
        }
        if(r.time<this.value.time){
            RecordList p = new RecordList(r);
            p.setNext(this);
            return p;
        }
        else{
            RecordList p = this;
            while(p.next!=null){
                if(p.next.value.time>r.time){
                    RecordList q = new RecordList(r);
                    q.setNext(p.next);
                    p.next = q;
                    return this;
                }
                p=p.next;
            }
            p.setNext(new RecordList(r));
            return this;
        }
    }

    public RecordList getNext() {
        return next;
    }
    public RecordList update(Record r){
        RecordList p = this;
        p= p.deleteRecord(r);
        p = p.addRecord(r);
        return p;
    }
    public RecordList deleteRecord(Record r){
        RecordList p = this;
        if(p.getNext()==null){
            if(p.value.code.equals(r.code)){
                return null;
            }
            else{
                return p;
            }
        }
        else if(p.value.code.equals(r.code)){
            return p.getNext();
        }
        else{
            p.next=p.next.deleteRecord(r);
        }
        return p;
    }
    public RecordList changeName(String code, String name){
        RecordList p=this;
        if(p.next==null) {
            if (p.value.code.equals(code)) {
                p.value.nickname = name;

            }
        }
        else{
            if(p.value.code.equals(code)){
                p.value.nickname=name;

            }
            else{
                p.next=p.next.changeName(code,name);
            }
        }
        return p;
    }
    public RecordList addRecord(Record r){
        RecordList p;
        if(this.getNext() == null){


            if( this.value.time>r.time){

                p = new RecordList(r, this);

            }
            else{
                p =this;
                p.setNext(new RecordList(r));
            }
        }
        else {
            if (this.value.time > r.time) {

                p = new RecordList(r, this);

            } else {
                p = this;
                p.setNext(p.getNext().addRecord(r));
            }
        }
        return p;
    }
}
