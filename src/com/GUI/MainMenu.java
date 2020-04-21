package com.GUI;

import com.GameControllers.Controller;
import com.GameControllers.GameObject;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainMenu extends Application {

    public static void main(String[] args) { launch(args); }

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        GameObject gameObject = controller.createGameObject();
        //System.out.println(gameObject.getObjectType().toString());
    }
}
