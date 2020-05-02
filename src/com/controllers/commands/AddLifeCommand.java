package com.controllers.commands;

import com.gui.Game;

import java.io.FileNotFoundException;

public class AddLifeCommand implements ICommand {

    Game game;
    public AddLifeCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        try {
            game.addLife();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
