package com.controllers;

import com.controllers.commands.ICommand;

public class RemoteControl {

    private ICommand command;
    private static RemoteControl instance;

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }

    private RemoteControl(){};
    public static RemoteControl getInstance(){
        if (instance==null)
            instance = new RemoteControl();
        return instance;
    }
}
