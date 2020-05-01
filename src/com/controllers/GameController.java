package com.controllers;

import com.controllers.commands.RemoveLifeCommand;
import com.gui.Game;


import com.objects.Fruit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

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
        for(GameObject gameObject: game.getGameState().getGameObjectList())
        {
            if (gameObject.hasMovedOffScreen()) {
                game.removeOffScreenObject(gameObject);
                if (!gameObject.isSliced() && gameObject instanceof Fruit) {
                    RemoteControl.getInstance().setCommand(new RemoveLifeCommand(game));
                    RemoteControl.getInstance().pressButton();
                }

            }
            else
                gameObject.move(game.getGameState().getTimeFrames());
        }
    }

    @Override
    public void sliceObjects() {
        Game game = Game.getInstance();
        for (GameObject gameObject : game.getGameState().getGameObjectList()) {
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
        Game game = Game.getInstance();
        Memento memento = game.getGameState().save();
        JAXBContext jaxbContext= null;
        try {
            jaxbContext = JAXBContext.newInstance(Memento.class);
            Marshaller marshaller=jaxbContext.createMarshaller();
            marshaller.marshal(memento,new File("GameState.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void loadGame() {
        Game game = Game.getInstance();
        Memento memento;
        JAXBContext jaxbContext= null;
        try {
            jaxbContext = JAXBContext.newInstance(Memento.class);
            Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
            memento = (Memento) unmarshaller.unmarshal(new File("GameState.xml"));
            game.setGameState(memento.getState());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetGame() {
        Game game = Game.getInstance();
        game.getGameState().setGameObjectList(new CopyOnWriteArrayList<>());
        game.setImageList(new ArrayList<>());
        game.getGameState().setTimeSeconds(0);
        game.getGameState().setTimeFrames(0);
        game.getGameState().setLives(3);
        game.getGameState().setScore(0);
    }
}
