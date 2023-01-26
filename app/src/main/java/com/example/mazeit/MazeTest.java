package com.example.mazeit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MazeTest extends AppCompatActivity implements View.OnClickListener {
    ImageButton up;
    Maze M =new Maze(5);
    ImageButton down;
    ImageButton left;
    ImageButton right;
    Button btn;
    ImageView iv;
    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_test);
        btn=findViewById(R.id.setnum);
        edt=findViewById(R.id.number);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        right.setOnClickListener(this);
        left.setOnClickListener(this);
        iv=  findViewById(R.id.iv);
        btn.setOnClickListener(this);
        M.RandomMaze();
        iv.setImageBitmap(M.DrawMaze(Color.BLACK,Color.GREEN,Color.RED,Color.GREEN));;
    }

    @Override
    public void onClick(View v) {
        if(v==up){
            M.tryup();
            if(M.solved()){
                M.RandomMaze();
            }
            iv.setImageBitmap(M.DrawMaze(Color.BLACK,Color.GREEN,Color.RED,Color.GREEN));;

        }
        if(v==down){
            M.trydown();
            if(M.solved()){
                M.RandomMaze();
            }
            iv.setImageBitmap(M.DrawMaze(Color.BLACK,Color.GREEN,Color.RED,Color.GREEN));;

        }
        if(v==left){
            M.tryleft();
            if(M.solved()){
                M.RandomMaze();
            }
            iv.setImageBitmap(M.DrawMaze(Color.BLACK,Color.GREEN,Color.RED,Color.GREEN));;

        }if(v==right){
            M.tryright();
            if(M.solved()){
                M.RandomMaze();
            }
            iv.setImageBitmap(M.DrawMaze(Color.BLACK,Color.GREEN,Color.RED,Color.GREEN));;

        }
        if(v==btn){
            M = new Maze(Integer.parseInt(edt.getText().toString()));
            M.RandomMaze();
            iv.setImageBitmap(M.DrawMaze(Color.BLACK,Color.GREEN,Color.RED,Color.GREEN));;
        }

    }
}