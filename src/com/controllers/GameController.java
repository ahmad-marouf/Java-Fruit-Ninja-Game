package com.controllers;

import com.gui.Game;


import com.objects.Object;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.List;
import java.util.Random;

public class GameController implements GameActions {



    @Override
    public GameObject createGameObject() {
        ENUM objectType;
        Random random = new Random();
        ObjectFactory factory = new ObjectFactory();
        objectType = ENUM.values()[random.nextInt(ENUM.values().length)];
        GameObject gameObject = factory.getGameObject(objectType);

        return gameObject;
    }

    @Override
    public void updateObjectsLocations() {
        // get objects list from game gui
        Game game=Game.getInstance();
        for(GameObject gameObject: game.getGameObjectList())
        {
            gameObject.move(game.getTimeFrames());
        }
    }

    @Override
    public void sliceObjects() {
        Game game = Game.getInstance();
        for (GameObject gameObject : game.getGameObjectList()) {
            if (    game.getMouseX() >= gameObject.getXlocation() &&
                    game.getMouseX() <= (gameObject.getXlocation() + gameObject.getBufferedImages()[0].getWidth()) &&
                    game.getMouseY() >= gameObject.getYlocation() &&
                    game.getMouseY() <= (gameObject.getYlocation() + gameObject.getBufferedImages()[0].getHeight()) &&
                    !gameObject.isSliced()
            )
                gameObject.slice();

        }
    }

    @Override
    public void saveGame() {

    }

    @Override
    public void loadGame() {

    }

    @Override
    public void ResetGame() {

    }
}
