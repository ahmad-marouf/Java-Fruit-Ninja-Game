package com.objects;

import com.controllers.ENUM;
import com.controllers.RemoteControl;
import com.controllers.commands.RemoveLifeCommand;
import com.gui.Game;

public class DangerousBomb extends Object {

    @Override
    public void slice() {
        super.slice();
        RemoteControl.getInstance().setCommand(new RemoveLifeCommand(Game.getInstance()));
        RemoteControl.getInstance().pressButton();
    }

    public DangerousBomb () {
        super(ENUM.DANGEROUS);
    }

}
