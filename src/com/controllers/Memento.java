package com.controllers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GameState")
@XmlAccessorType(XmlAccessType.FIELD)
public class Memento {

    @XmlElement(name = "state")
    private GameState state;

    public Memento(){}
    public Memento(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }
}
