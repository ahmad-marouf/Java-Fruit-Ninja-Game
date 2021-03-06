package com.controllers;

import com.objects.*;

public class ObjectFactory {

    public GameObject getGameObject(ENUM objectType) {
        if (objectType == null)
            return null;
        else if (objectType == ENUM.APPLE)
            return new Apple();
        else if (objectType == ENUM.ORANGE)
            return new Orange();
        else if (objectType == ENUM.PINEAPPLE)
            return new Pineapple();
        else if (objectType == ENUM.STRAWBERRY)
            return new Strawberry();
        else if (objectType == ENUM.WATERMELON)
            return new Watermelon();
        else if (objectType == ENUM.STARFRUIT)
            return new Starfruit();
        else if (objectType == ENUM.DRAGONFRUIT)
            return new DragonFruit();
        else if (objectType == ENUM.FATAL)
            return new FatalBomb();
        else if (objectType == ENUM.DANGEROUS)
            return new DangerousBomb();
        return null;
    }
}
