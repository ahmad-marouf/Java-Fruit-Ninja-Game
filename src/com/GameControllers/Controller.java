package com.GameControllers;

import com.Fruits.Object;

import java.util.Random;

public class Controller implements GameActions {

    @Override
    public GameObject createGameObject() {
        Random random = new Random();
        ObjectFactory factory = new ObjectFactory();
        GameObject gameObject= null;
        switch (random.nextInt(ENUM.values().length)) {
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
        GameObject gameObject=createGameObject();
        gameObject.move(5);

    }

    @Override
    public void sliceObjects() {

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
