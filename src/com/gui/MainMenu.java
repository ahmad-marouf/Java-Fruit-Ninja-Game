package com.gui;

import com.controllers.GameController;
import com.controllers.Scores;
import com.levels.Easy;
import com.levels.Medium;
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
        Scores scores= Scores.getInstance();
       // scores.load();
        Game game = Game.getInstance();
        game.setPrimaryStage(primaryStage);
        game.setMainMenuScene(menuScene);
        GameController gameController = new GameController();
//        gameController.loadGame();
        game.getGameState().setDifficulty(new Easy());
        game.startGame();
       
    }
}
