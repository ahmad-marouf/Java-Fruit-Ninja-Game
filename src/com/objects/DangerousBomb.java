package com.objects;

import com.controllers.ENUM;
import com.controllers.RemoteControl;
import com.controllers.commands.RemoveLifeCommand;
import com.gui.Game;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class DangerousBomb extends Bomb {

    @Override
    public void slice() {
        super.slice();
        RemoteControl.getInstance().setCommand(new RemoveLifeCommand(Game.getInstance()));
        RemoteControl.getInstance().pressButton();
        String mediaFile = "Sounds\\dangerous explosion.mp3";
        Media sliceSound = new Media(new File(mediaFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sliceSound);
        mediaPlayer.play();
    }

    public DangerousBomb () {
        super(ENUM.DANGEROUS);
    }

}
