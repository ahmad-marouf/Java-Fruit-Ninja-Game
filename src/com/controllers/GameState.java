package com.controllers;

import com.levels.Difficulty;
import com.objects.Object;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@XmlRootElement(name = "state")
@XmlAccessorType(XmlAccessType.FIELD)
public class GameState {

    @XmlElement(name = "object")
    private List<Object> gameObjectList;
    @XmlElement(name = "timeSeconds")
    private int timeSeconds;
    @XmlElement(name = "timeFrames")
    private int timeFrames;
    @XmlElement(name = "score")
    private int score;
    @XmlElement(name = "lives")
    private int lives;
    @XmlElement(name = "spawnRate")
    private double spawnRate;
    @XmlElement(name = "difficulty")
    private String difficulty;

    public GameState(){

    }
    public GameState(List<GameObject> gameObjectList, int timeSeconds, int timeFrames, int score, int lives) {
        this.gameObjectList = new CopyOnWriteArrayList<>();
        for (GameObject gameObject : gameObjectList)
            this.gameObjectList.add((Object) gameObject);
        this.timeSeconds = timeSeconds;
        this.timeFrames = timeFrames;
        this.score = score;
        this.lives = lives;
    }

    public Memento save() {
        return new Memento(this);
    }

    public List<Object> getGameObjectList() { return gameObjectList; }

    public void setGameObjectList(List<Object> gameObjectList) { this.gameObjectList = gameObjectList; }

    public int getTimeSeconds() { return timeSeconds; }

    public void setTimeSeconds(int timeSeconds) { this.timeSeconds = timeSeconds; }

    public int getTimeFrames() { return timeFrames; }

    public void setTimeFrames(int timeFrames) { this.timeFrames = timeFrames; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public int getLives() { return lives; }

    public void setLives(int lives) { this.lives = lives; }

    public double getSpawnRate() { return spawnRate; }

    public void setSpawnRate(double spawnRate) { this.spawnRate = spawnRate; }

    public String getDifficulty() { return difficulty; }

    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty.toString(); }
}
