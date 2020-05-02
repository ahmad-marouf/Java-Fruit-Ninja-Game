package com.objects;

import com.controllers.ENUM;
import com.gui.Game;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public abstract class Fruit extends Object {

    private int points;

    @Override
    public void slice() {
        super.slice();
        Game game = Game.getInstance();
        game.getGameState().setScore(game.getGameState().getScore() + points);
        String mediaFile = "Sounds\\slash sound.mp3";
        Media sliceSound = new Media(new File(mediaFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sliceSound);
        mediaPlayer.play();
    }

    public Fruit(ENUM objectType, int points) {
        super(objectType);
        this.points = points;
    }
}
