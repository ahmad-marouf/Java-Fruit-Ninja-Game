package com.objects;

import com.controllers.ENUM;
import com.controllers.RemoteControl;
import com.controllers.commands.AddLifeCommand;
import com.gui.Game;

public class Starfruit extends Fruit {

    @Override
    public void slice() {
        super.slice();
        RemoteControl.getInstance().setCommand(new AddLifeCommand(Game.getInstance()));
        RemoteControl.getInstance().pressButton();
    }

    public Starfruit() {
        super(ENUM.STARFRUIT, 20);
    }
}
