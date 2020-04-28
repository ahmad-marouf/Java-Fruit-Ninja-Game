package com.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {        

        VBox menuButtons = new VBox(20);
        Scene menuScene = new Scene(menuButtons, 600, 600);
        primaryStage.setScene(menuScene);
        primaryStage.show();
        Game game = Game.getInstance();
        game.startGame(primaryStage);
       
    }
}
