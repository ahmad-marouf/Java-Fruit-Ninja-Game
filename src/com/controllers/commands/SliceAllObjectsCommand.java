package com.controllers.commands;

import com.controllers.GameObject;
import com.gui.Game;
import com.objects.Fruit;

public class SliceAllObjectsCommand implements ICommand {

    Game game;
    public SliceAllObjectsCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        for (GameObject gameObject : game.getGameState().getGameObjectList())
            if (gameObject instanceof Fruit && !gameObject.isSliced())
                gameObject.slice();
    }
}
