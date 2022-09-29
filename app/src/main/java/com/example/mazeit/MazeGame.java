package com.example.mazeit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MazeGame extends AppCompatActivity implements View.OnClickListener {
    TextView score;
    TextView starsnum;
    Button publishRec;
    int curr;
    boolean active = false;
    Button playagain;
    Button menu;
    TextView rec;
    TextView timer;
    Dialog d;
    long startTime = 0;
    SharedPreferences sp;
    ImageButton up;
    Maze M=new Maze(5);
    ImageButton down;
    ImageButton left;
    ImageButton right;

    ImageView iv;
    GameEx game = new GameEx(M,0,0,5);
    private void sendMessage(String massage)
    {


        Intent intent
                = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");

        intent.putExtra(
                Intent.EXTRA_TEXT,
                massage);

        startActivity(intent);
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
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            long millis = System.currentTimeMillis() - startTime;

            if(game.getDestination()>game.getScore()) {
                timerHandler.postDelayed(this, 10);
                game.update(1);
                timer.setText(totext(millis / 10));
            }
            else{
                game.setCentisecs((int)millis/10);
                timer.setText(totext(millis / 10));

                openDialogue();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maze_game);
        rec = findViewById(R.id.newrec);
        timer = findViewById(R.id.time);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        sp=getSharedPreferences("scores",0);

        curr = sp.getInt(""+game.getMaze().getsize(),100000000);
        right.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);

        int k = getIntent().getExtras().getInt("size");
        iv = findViewById(R.id.iv);
        M= new Maze(k);
        M.RandomMaze();
        game = new GameEx(M,0,0,5);
        iv.setImageBitmap(M.DrawMaze());

    }

    @Override
    public void onClick(View view) {
        if(!active){
            active = true;
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 10);
        }
        if(view == playagain){
            Intent intent=new Intent(this,MazeGame.class);
            intent.putExtra("size",game.getMaze().getsize());
            startActivity(intent);
        }
        if(view==publishRec){
            if(curr>game.getCentisecs()){
                sendMessage("O M G!!!1!11! I just broke my record in MazeIt!!!! My new record on the " + M.getsize() + "x" + M.getsize() + " map is " + totext((long) game.getCentisecs()) + "!!! can YOU break it? I wanna see you try");
            }
            else{
                sendMessage("Hi all! I just beat the "+M.getsize()+"x"+M.getsize()+"map in MazeIt in " +totext((long) game.getCentisecs())+"! check this game out.");
            }
        }
        if(view == menu){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if (game.getDestination() > game.getScore()) {
            if (view == right) {
                game.tryright();
                if (game.getMaze().solved()) {
                    game.getMaze().RandomMaze();
                    game.setScore(game.getScore() + 1);
                }
            }
            if (view == left) {
                game.tryleft();
                if (game.getMaze().solved()) {
                    game.getMaze().RandomMaze();
                    game.setScore(game.getScore() + 1);
                }
            }
            if (view == up) {
                game.tryup();
                if (game.getMaze().solved()) {
                    game.getMaze().RandomMaze();
                    game.setScore(game.getScore() + 1);
                }
            }
            if (view == down) {
                game.trydown();
                if (game.getMaze().solved()) {
                    game.getMaze().RandomMaze();
                    game.setScore(game.getScore() + 1);
                }
            }
            iv.setImageBitmap(game.getMaze().DrawMaze());
        }

    }
    @SuppressLint("SetTextI18n")
    public void openDialogue(){
        d= new Dialog(this);

        d.setContentView(R.layout.showscore_dialog);
        d.setTitle("score");
        d.setCancelable(false);

        publishRec = d.findViewById(R.id.publishRec);
        publishRec.setOnClickListener(this);
        starsnum =(TextView) d.findViewById(R.id.stars);
        playagain = (Button) d.findViewById(R.id.replay);
        menu = (Button) d.findViewById(R.id.button);
        playagain.setOnClickListener(this);
        menu.setOnClickListener(this);
        score = (TextView) d.findViewById(R.id.score);
        score.setText(totext((long) game.getCentisecs()));
        rec = (TextView) d.findViewById(R.id.newrec);

        rec.setText(""+curr);


        if (curr>game.getCentisecs()){
            rec.setText("new record!");
            publishRec.setEnabled(true);
            SharedPreferences.Editor editor=sp.edit();
            editor.putInt(""+game.getMaze().getsize(), game.getCentisecs());

            if(game.getCentisecs()<sp.getInt("1star"+game.getMaze().getsize(),10000000)){
                editor.putInt("star"+game.getMaze().getsize(),1);
            }
            if(game.getCentisecs()<sp.getInt("2star"+game.getMaze().getsize(),10000000)){
                editor.putInt("star"+game.getMaze().getsize(),2);
            }
            if(game.getCentisecs()<sp.getInt("3star"+game.getMaze().getsize(),10000000)){
                editor.putInt("star"+game.getMaze().getsize(),3);
            }
            editor.commit();

        }
        else{
            rec.setText("best: "+totext((long)curr));
        }
        //int b = sp.getInt("star"+game.getMaze().getsize(),0);
        int p =0;
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(""+game.getMaze().getsize(), game.getCentisecs());
        if(game.getCentisecs()<sp.getInt("1star"+game.getMaze().getsize(),10000000)){
            p=1;
        }
        if(game.getCentisecs()<sp.getInt("2star"+game.getMaze().getsize(),10000000)){
            p=2;
        }
        if(game.getCentisecs()<sp.getInt("3star"+game.getMaze().getsize(),10000000)){
            p=3;}
        starsnum.setText("you have "+ p+" stars!");
        d.show();
    }
}