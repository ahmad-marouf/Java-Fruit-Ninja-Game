package com.objects;

import com.controllers.ENUM;
import com.gui.Game;

public class FatalBomb extends Object {

    @Override
    public void slice() {
        super.slice();
        Game.getInstance().gameOver();
    }

    public FatalBomb() {
        super(ENUM.FATAL);
    }

}
