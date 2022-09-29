package com.example.mazeit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Maze {
    private int n;
    private int[] location = {0,0};
    private int[] end = {n-1,n-1};
    private boolean[][] left;
    private boolean[][] up;
    public int getsize(){
        return n;
    }
    public Bitmap DrawMaze() {
        int k=900;
        Bitmap bitmap = Bitmap.createBitmap(k, k, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paintCircle = new Paint();
        Paint paintCircleend = new Paint();
        Paint paintBg = new Paint();
        Paint stroke = new Paint();
        canvas.drawRect(0, 0, k, k, paintBg);
        paintBg.setColor(Color.GRAY);
        paintCircle.setColor(Color.GREEN);
        paintCircleend.setColor(Color.RED);
        stroke.setColor(Color.GREEN);
        paintCircle.setAntiAlias(true);
        stroke.setStrokeWidth(k/12/n);
        canvas.drawCircle(k/2 / n + k * location[0] / n, k/2 / n + k * location[1] / n, k/3 / n, paintCircle);
        paintCircleend.setAntiAlias(true);
        canvas.drawCircle(k*(end[0]+1)/n-k/2/n, k*(end[1]+1)/n-(k/2)/n, k/3 / n, paintCircleend);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(left[i][j]){
                    canvas.drawLine(k*i/n, k*j/n, k*i/n, k*j/n+k/n, stroke);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(up[i][j]){
                    canvas.drawLine(k*i/n, k*j/n, k*i/n+k/n, k*j/n, stroke);
                }
            }
        }
        canvas.drawLine(0,0,k,0,stroke);
        canvas.drawLine(0,k,k,k,stroke);
        canvas.drawLine(0,0,0,k,stroke);
        canvas.drawLine(k,0,k,k,stroke);

        return bitmap;
    }
    public String tostring(boolean[][] b){
        String s="";
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(b[j][i]){
                    s+="1";
                }
                else{
                    s+="0";
                }
            }
            s+="\n";
        }
        return s;
    }
    public String tosleft(){
        return this.tostring(left);
    }
    public String tosup(){
        return this.tostring(up);
    }
    public void changethingy(int i){
        if(i<n*n){
            left[i%n][i/n]=!left[i%n][i/n];
        }
        else {
            i-=n*(n);
            up[i%n][i/n]=!up[i%n][i/n];
        }
    }
    public void changeend(){
        int[][] status = new int[n][n];
        // 0 - unreached
        // 1 - newly reached
        // 2 - on process
        // 3 - processed
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++) {
                status[i][j] = 0;
            }
        }
        int[] loc = {0,0};
        status[0][0]=1;
        boolean active = true;
        while (active){

            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++) {
                    if (status[i][j]==1) {
                        end[0]=i;end[1]=j;
                        status[i][j] = 2;
                    }
                }
            }

            for(int i=0;i<n;i++) {
                for (int j = 0; j < n; j++) {
                    if (status[i][j] == 2) {
                        loc[0]=i; loc[1]=j;
                        if (this.canright(loc)) {
                            if (status[i + 1][j] == 0) {
                                status[i + 1][j] = 1;
                            }
                        }
                        if (this.canleft(loc)) {
                            if (status[i - 1][j] == 0) {
                                status[i - 1][j] = 1;
                            }
                        }
                        if (this.canup(loc)) {
                            if (status[i][j - 1] == 0) {
                                status[i][j - 1] = 1;
                            }
                        }
                        if (this.candown(loc)) {
                            if (status[i][j + 1] == 0) {
                                status[i][j + 1] = 1;
                            }
                        }
                    }
                }
            }
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++) {
                    if (status[i][j]==2){
                        status[i][j]=3;}
                }
            }
            active = false;
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++) {
                    if (status[i][j]==1){
                        active = true;
                    }
                }
            }
        }

    }
    public void RandomMaze(){
        this.emptymaze();
        int[] ar = new int[2*n*n];
        for(int i=0;i<2*n*n;i++){
            ar[i]=i;
        }
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        for(int i=0;i<2*n*n;i++){
            this.changethingy(ar[i]);
            if(!this.connectable()){
                this.changethingy(ar[i]);
            }
        }
        for(int i=0;i<n;i++){
            up[i][0]=true;
        }
        for(int i=0;i<n;i++){
            left[0][i]=true;
        }
        changeend();
    }
    public Maze(int n, int[] location, boolean[][] left, boolean[][] up) {
        this.n = n;
        this.location = location;
        this.left = left;
        this.up = up;
    }
    public  Maze(int n){
        this.n=n;
        location[0]=0;location[1]=0;
        up = new boolean[n][n];
        left = new boolean[n][n];
        for(int i=0;i<n;i++){
            up[i][0]=true;
        }
        for(int i=0;i<n;i++){
            left[0][i]=true;
        }
    }
    public void emptymaze(){
        location[0]=0;location[1]=0;
        up = new boolean[n][n];
        left = new boolean[n][n];
        for(int i=0;i<n;i++){
            up[i][0]=true;
        }
        for(int i=0;i<n;i++){
            left[0][i]=true;
        }
    }
    public void moveright(){
        location[0] = location[0]+1;
    }
    public void moveleft(){
        location[0] = location[0]-1;
    }
    public void moveup(){
        location[1] = location[1]-1;
    }
    public void movedown(){
        location[1] = location[1]+1;
    }
    public boolean canleft(){
        return canleft(location);
    }
    public boolean canleft(int[] loc){
        if(loc[0]==0){
            return false;
        }
        else{
            return !left[loc[0]][loc[1]];
        }
    }
    public boolean canright(){
        return canright(location);
    }
    public boolean canright(int[] loc){
        if(loc[0]==n-1){
            return false;
        }
        else{
            return !left[loc[0]+1][loc[1]];
        }
    }
    public boolean canup(){
        return canup(location);
    }
    public boolean canup(int[] loc){
        if(loc[1]==0){
            return false;
        }
        else{
            return !up[loc[0]][loc[1]];
        }
    }
    public boolean candown(){
        return candown(location);
    }
    public boolean candown(int[] loc){
        if(loc[1]==n-1){
            return false;
        }
        else{
            return !up[loc[0]][loc[1]+1];
        }
    }
    public void tryup(){
        if(this.canup()){this.moveup();}
    }
    public void trydown(){
        if(this.candown()){this.movedown();}
    }
    public void tryleft(){
        if(this.canleft()){this.moveleft();}
    }
    public void tryright(){
        if(this.canright()){this.moveright();}
    }
    public boolean solved(){
        if(location[0]==end[0]&&location[1]==end[1]){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean connectable(){
        int[][] status = new int[n][n];
        // 0 - unreached
        // 1 - newly reached
        // 2 - on process
        // 3 - processed
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++) {
                status[i][j] = 0;
            }
        }
        int[] loc = {0,0};
        status[0][0]=1;
        boolean active = true;
        while (active){

            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++) {
                    if (status[i][j]==1) {
                        status[i][j] = 2;
                    }
                }
            }

            for(int i=0;i<n;i++) {
                for (int j = 0; j < n; j++) {
                    if (status[i][j] == 2) {
                        loc[0]=i; loc[1]=j;
                        if (this.canright(loc)) {
                            if (status[i + 1][j] == 0) {
                                status[i + 1][j] = 1;
                            }
                        }
                        if (this.canleft(loc)) {
                            if (status[i - 1][j] == 0) {
                                status[i - 1][j] = 1;
                            }
                        }
                        if (this.canup(loc)) {
                            if (status[i][j - 1] == 0) {
                                status[i][j - 1] = 1;
                            }
                        }
                        if (this.candown(loc)) {
                            if (status[i][j + 1] == 0) {
                                status[i][j + 1] = 1;
                            }
                        }
                    }
                }
            }
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++) {
                    if (status[i][j]==2){
                    status[i][j]=3;}
                }
            }
            active = false;
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++) {
                    if (status[i][j]==1){
                        active = true;
                    }
                }
            }


        }
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++) {
                if (status[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }


}
