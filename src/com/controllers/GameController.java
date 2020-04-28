package com.controllers;

import com.gui.Game;


import com.objects.Object;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.List;
import java.util.Random;

public class GameController implements GameActions {



    @Override
    public GameObject createGameObject() {
        Random random = new Random();
        ObjectFactory factory = new ObjectFactory();
        GameObject gameObject = null;
        switch (random.nextInt(ENUM.values().length) + 1) {
            case 1:
               gameObject = factory.getGameObject(ENUM.APPLE);
                break;
            case 2:
                gameObject = factory.getGameObject(ENUM.ORANGE);
                break;
            case 3:
                gameObject = factory.getGameObject(ENUM.STRAWBERRY);
                break;
            case 4:
                gameObject = factory.getGameObject(ENUM.WATERMELON);
                break;
            case 5:
                gameObject = factory.getGameObject(ENUM.PINEAPPLE);
                break;
            case 6:
                gameObject = factory.getGameObject(ENUM.FATAL);
                break;
            case 7:
                gameObject = factory.getGameObject(ENUM.DANGEROUS);
                break;

        }
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
