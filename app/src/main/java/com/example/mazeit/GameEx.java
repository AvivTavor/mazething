package com.example.mazeit;

public class GameEx {
    private Maze maze;
    private int centisecs;
    private int score;
    private int destination;
    //requirement by firebase
    public GameEx(){}
    public GameEx(Maze maze, int centisecs, int score, int destination) {
        this.maze = maze;
        this.centisecs = centisecs;
        this.score = score;
        this.destination = destination;
    }
    public Maze getMaze() {
        return maze;
    }
    public void setCentisecs(int centisecs) {
        this.centisecs = centisecs;
    }
    public int getCentisecs() {
        return centisecs;
    }
    public void setDestination(int destination) {
        this.destination = destination;
    }
    public int getScore(){
        return score;
    }
    public void setMaze(Maze maze) {
        this.maze = maze;
    }
    public int getDestination(){
        return destination;
    }
    public void setScore(int score){
        this.score=score;
    }
    public void update(int time){
        centisecs+=time;
    }
    public void tryright(){
        maze.tryright();
    }
    public void tryleft(){
        maze.tryleft();
    }
    public void tryup(){
        maze.tryup();
    }
    public void trydown(){
        maze.trydown();
    }
}
