package com.example.mazeit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class MazeGame extends AppCompatActivity implements View.OnClickListener {
    TextView score;
    public int tocolor(String s) {
        if (s.equals("green")) {
            return Color.GREEN;
        }
        if (s.equals("blue")) {
            return Color.BLUE;
        }
        if (s.equals("red")) {
            return Color.RED;
        }
        if (s.equals("black")) {
            return Color.BLACK;
        }
        if (s.equals("white")) {
            return Color.WHITE;
        }
        if (s.equals("dark gray")) {
            return Color.DKGRAY;
        }
        if (s.equals("yellow")) {
            return Color.YELLOW;
        }
        if (s.equals("cyan")) {
            return Color.CYAN;
        } if (s.equals("magenta")) {
            return Color.MAGENTA;
        }
        if (s.equals("light gray")) {
            return Color.LTGRAY;
        }
        else{
            return 0;
        }
    }
    public void onPause() {
        super.onPause();
        mpmusic.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        //Play again
        mpmusic.start();
        //do more stuff
    }
    @Override
    public void onBackPressed() {
        mpmusic.stop();
        Intent intent=new Intent(this,levelmenu.class);
        startActivity(intent);
    }
    public void uploadRecord(Record record, DatabaseReference Ref){
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
                        MazeGame.this,
                        "upload failed, please try again later.",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }

    TextView starsnum;
    Button publishRec;
    ImageButton backbutton;
    int curr;
    String[] keys = {"-N2mbGLyLvWUTvF6b-DI","-N2mbGM4FBgBFIeg-hp-","-N2mbGM5GFwxXNH3wJI1","-N2mbGM6L3Y7hhQeVOth","-N2mbGM7KitQ6d1AZhMq","-N2mbGM8yoBClOw6SkEj","-N2mbGM9rEZ7__NZnWn9","-N2mbGMADsp21vMmCwbL","-N2mbGMB1NQfvbCfm1pj","-N2mbGMCCZgd14S0wkTx"};
    boolean active = false;

    FirebaseDatabase firebaseDatabase;

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
    MediaPlayer mpmusic;
    MediaPlayer mpclap;
    MediaPlayer bpress;
    MediaPlayer nextmaze;
    ImageButton restart;
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
        firebaseDatabase = FirebaseDatabase.getInstance("https://mazeit-5ca2d-default-rtdb.europe-west1.firebasedatabase.app/");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_game);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final ConstraintLayout Clayout;
        Clayout = findViewById(R.id.thescreen);
        bpress = MediaPlayer.create(getApplicationContext(), R.raw.blop);

        mpmusic = MediaPlayer.create(getApplicationContext(), R.raw.main);
        mpmusic.setLooping(true);
        restart = findViewById(R.id.restart);
        restart.setOnClickListener(this);

        nextmaze = MediaPlayer.create(getApplicationContext(), R.raw.nextmaze);
        mpmusic.start();
        mpclap = MediaPlayer.create(getApplicationContext(), R.raw.claps);
        rec = findViewById(R.id.newrec);
        timer = findViewById(R.id.time);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(this);
        sp=getSharedPreferences("scores",0);
        Clayout.setBackgroundColor(sp.getInt("backgroundcolor",Color.BLACK));
        if(sp.getInt("backgroundcolor",Color.BLACK)==Color.BLACK||sp.getInt("backgroundcolor",Color.BLACK)==Color.DKGRAY){
            timer.setTextColor(Color.WHITE);
        }
        else{
            timer.setTextColor(Color.BLACK);
        }
        mpmusic.setVolume((float)sp.getInt("musicvol",10)/10,(float)sp.getInt("musicvol",10)/10);
        bpress.setVolume((float)sp.getInt("sfxvol",10)/10,(float)sp.getInt("sfxvol",10)/10);
        mpclap.setVolume((float)sp.getInt("sfxvol",10)/10,(float)sp.getInt("sfxvol",10)/10);
        nextmaze.setVolume((float)sp.getInt("sfxvol",10)/10,(float)sp.getInt("sfxvol",10)/10);


        right.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);

        int k = getIntent().getExtras().getInt("size");
        iv = findViewById(R.id.iv);
        M= new Maze(k);

        M.RandomMaze();
        game = new GameEx(M,0,0,5);
        iv.setImageBitmap(M.DrawMaze(sp.getInt("backgroundcolor",Color.BLACK),sp.getInt("playercolor",Color.GREEN),sp.getInt("finishcolor",Color.RED),sp.getInt("mazecolor",Color.GREEN)));

        curr = sp.getInt(""+game.getMaze().getsize(),100000000);

    }
    @Override
    public void onClick(View view) {
        if(!active){
            active = true;
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 10);
        }
        if(view ==restart){
            Intent intent=new Intent(this,MazeGame.class);
            intent.putExtra("size",game.getMaze().getsize());
            startActivity(intent);
        }
        if(view == backbutton){
            onBackPressed();
        }
        if(view == playagain){

            mpmusic.stop();
            Intent intent=new Intent(this,MazeGame.class);
            intent.putExtra("size",game.getMaze().getsize());
            startActivity(intent);
        }
        if(view==publishRec){
            if(curr>game.getCentisecs()){
                sendMessage("O M G!!!1!11! I just broke my record in MazeIt!!!! My new record on the " + M.getsize() + "x" + M.getsize() + " map is " + totext((long) game.getCentisecs()) + "!!! can YOU break it? I wanna see you try");
            }
            else{
                sendMessage("Hi all! I just beat the "+M.getsize()+"x"+M.getsize()+" map in MazeIt in " +totext((long) game.getCentisecs())+"! check this game out.");
            }
        }
        if(view == menu){

            mpmusic.stop();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if (game.getDestination() > game.getScore()) {
            if (view == right) {
                if(game.getMaze().canright()){
                    bpress.start();
                }
                game.tryright();
                if (game.getMaze().solved()) {
                    nextmaze.start();
                    game.getMaze().RandomMaze();
                    game.setScore(game.getScore() + 1);
                }
            }
            if (view == left) {
                if(game.getMaze().canleft()){
                    bpress.start();
                }
                game.tryleft();
                if (game.getMaze().solved()) {
                    nextmaze.start();
                    game.getMaze().RandomMaze();
                    game.setScore(game.getScore() + 1);
                }
            }
            if (view == up) {
                if(game.getMaze().canup()){
                    bpress.start();
                }
                game.tryup();
                if (game.getMaze().solved()) {
                    nextmaze.start();
                    game.getMaze().RandomMaze();
                    game.setScore(game.getScore() + 1);
                }
            }
            if (view == down) {
                if(game.getMaze().candown()){
                    bpress.start();
                }
                game.trydown();
                if (game.getMaze().solved()) {
                    nextmaze.start();
                    game.getMaze().RandomMaze();
                    game.setScore(game.getScore() + 1);
                }
            }
            iv.setImageBitmap(M.DrawMaze(sp.getInt("backgroundcolor",Color.BLACK),sp.getInt("playercolor",Color.GREEN),sp.getInt("finishcolor",Color.RED),sp.getInt("mazecolor",Color.GREEN)));
        }

    }
    @SuppressLint("SetTextI18n")
    public void openDialogue(){
        d= new Dialog(this);
        mpmusic.stop();
        mpclap.start();
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
        uploadRecord(new Record(getIntent().getExtras().getInt("size"),sp.getInt(""+getIntent().getExtras().getInt("size"),0),sp.getString("name",""),sp.getString("userid","")),firebaseDatabase.getReference("record").child(keys[getIntent().getExtras().getInt("size")-1]));
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