package com.objects;

import com.controllers.ENUM;
import com.controllers.RemoteControl;
import com.controllers.commands.AddLifeCommand;
import com.controllers.commands.SliceAllObjectsCommand;
import com.gui.Game;

public class DragonFruit extends Fruit {

    @Override
    public void slice() {
        super.slice();
        RemoteControl.getInstance().setCommand(new SliceAllObjectsCommand(Game.getInstance()));
        RemoteControl.getInstance().pressButton();
    }

    public DragonFruit() {
        super(ENUM.DRAGONFRUIT, 20);
    }
}
