package com.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game {

    public void startGame(Stage primaryStage) {
        Scene gameScene;
        Pane gameLayout = new Pane();

        Pane labelPane = new Pane();
        Label highScoreLabel = new Label("Highest Score: ");
        highScoreLabel.setLayoutY(10); highScoreLabel.setLayoutX(10);
        Label scoreLabel = new Label("Current Score: ");
        scoreLabel.setLayoutY(40); scoreLabel.setLayoutX(10);
        Label timeLabel = new Label("Time: ");
        timeLabel.setLayoutY(10); timeLabel.setLayoutX(380);
        Label livesLabel = new Label("Lives Remaining: ");
        livesLabel.setLayoutY(40); livesLabel.setLayoutX(380);

        Pane objectPane = new Pane();

        //Add Create Objects every time interval and display image


        //Add animate images movement


        //Add slicing mouse handling



        labelPane.getChildren().addAll(highScoreLabel, scoreLabel, timeLabel, livesLabel);
        gameLayout.getChildren().addAll(labelPane, objectPane);
        gameScene = new Scene(gameLayout, 600,400);
        primaryStage.setScene(gameScene);
    }
}
