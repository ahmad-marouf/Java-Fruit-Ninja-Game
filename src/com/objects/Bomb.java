package com.objects;

import com.controllers.ENUM;
import com.gui.Game;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public abstract class Bomb extends Object {

    @Override
    public void slice() {
        super.slice();
        String mediaFile = "Sounds\\slash sound.mp3";
        Media sliceSound = new Media(new File(mediaFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sliceSound);
        mediaPlayer.play();
    }

    public Bomb(ENUM objectType) {
        super(objectType);
    }
}
