package com.objects;

import com.controllers.ENUM;
import com.gui.Game;

public abstract class Fruit extends Object {

    private int points;

    @Override
    public void slice() {
        super.slice();
        Game game = Game.getInstance();
        game.getGameState().setScore(game.getGameState().getScore() + points);
    }

    public Fruit(ENUM objectType, int points) {
        super(objectType);
        this.points = points;
    }
}
