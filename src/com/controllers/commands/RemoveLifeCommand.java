package com.controllers.commands;

import com.gui.Game;

public class RemoveLifeCommand implements ICommand {

    Game game;
    public RemoveLifeCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.removeLife(game.getLivesDisplay());
    }
}
