package com.players;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "score")
@XmlAccessorType(XmlAccessType.FIELD)
public class Score implements Comparable<Score> {

    @XmlElement(name = "points")
    private int score;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "difficulty")
    private String difficulty;


    public int getScore() { return score; }

    public String getName() { return name; }

    public String getDifficulty() { return difficulty; }

    public void setScore(int score) { this.score = score; }

    public void setName(String name) { this.name = name; }

    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    @Override
    public int compareTo(Score o) {
        int compare = Integer.compare(o.getScore(), score);
        return compare;
    }
}
