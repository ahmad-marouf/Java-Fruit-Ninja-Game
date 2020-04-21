package com.GameControllers;

import com.Fruits.*;

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
        return null;
    }
}
