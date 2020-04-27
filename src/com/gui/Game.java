package com.gui;

import com.controllers.GameController;
import com.controllers.GameObject;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.List;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game {

    private List<ImageView> imageList = new ArrayList<>();
    private List<Object> objectList = new ArrayList<>();
    private GameObject gameObject;
    private int time;
    
    public void startGame(Stage primaryStage) {
        double falling;
        Scene gameScene;
        Pane gameLayout = new Pane();

        Pane labelPane = new Pane();
        Label highScoreLabel = new Label("Highest Score: ");
        highScoreLabel.setLayoutY(10);
        highScoreLabel.setLayoutX(10);
        Label scoreLabel = new Label("Current Score: ");
        scoreLabel.setLayoutY(40);
        scoreLabel.setLayoutX(10);
        Label timeLabel = new Label("Time: ");
        timeLabel.setLayoutY(10);
        timeLabel.setLayoutX(380);
        Label livesLabel = new Label("Lives Remaining: ");
        livesLabel.setLayoutY(40);
        livesLabel.setLayoutX(380);

        Pane objectPane = new Pane();

        //Add Create Objects every time interval and display image
        falling = 1000;
        time = 0;
        GameController gameController = new GameController();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(falling), e -> {
            time+=falling/1000;
            timeLabel.setText("Time: "+ time);
            GameObject gameObject = gameController.createGameObject();
            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(gameObject.getBufferedImages()[0], null));
            imageView.setLayoutX(gameObject.getXlocation());
            imageView.setLayoutY(gameObject.getYlocation());
            imageList.add(imageView);
            objectList.add(gameObject);
        }));
        timeline.setCycleCount(100);
        timeline.play();
        labelPane.getChildren().addAll(imageList);

        //Add animate images movement
        //Add slicing mouse handling
        labelPane.getChildren().addAll(highScoreLabel, scoreLabel, timeLabel, livesLabel);
        gameLayout.getChildren().addAll(labelPane, objectPane);
        gameScene = new Scene(gameLayout, 600, 400);
        primaryStage.setScene(gameScene);
    }
}
