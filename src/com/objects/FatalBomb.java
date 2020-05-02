package com.objects;

import com.controllers.ENUM;
import com.gui.Game;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class FatalBomb extends Bomb {

    @Override
    public void slice() {
        super.slice();
        Game.getInstance().gameOver();
        String mediaFile = "Sounds\\fatal explosion.mp3";
        Media sliceSound = new Media(new File(mediaFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sliceSound);
        mediaPlayer.play();
    }

    public FatalBomb() {
        super(ENUM.FATAL);
    }

}
