package com.gui;

import com.controllers.GameController;
import com.controllers.GameObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.levels.Difficulty;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.bind.JAXBException;

public class Game {

    Scene gameScene;
    private List<ImageView> imageList = new ArrayList<>();
    private List<GameObject> gameObjectList = new ArrayList<>();
    private int timeSeconds;
    private int timeFrames;
    private int score;
    private double mouseX;
    private double mouseY;
    private double spawnRate;
    private static Game instance;
    private Difficulty difficulty;


    public void startGame(Stage primaryStage) {
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
        spawnRate = difficulty.getSpawnRate();
        timeSeconds = 0;
        timeFrames = 0;
        GameController gameController = new GameController();


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(spawnRate), e -> {
            timeSeconds += spawnRate /1000;
            timeLabel.setText("Time: "+ timeSeconds);
            GameObject gameObject = gameController.createGameObject();
            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(gameObject.getBufferedImages()[0], null));
            imageView.setLayoutX(gameObject.getXlocation());
            imageView.setLayoutY(gameObject.getYlocation());
            imageList.add(imageView);
            gameObjectList.add(gameObject);
        }));
        timeline.setCycleCount(100);
        timeline.play();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeFrames++;
                scoreLabel.setText("Current Score: " + score);
                //move imageViews on screen
                int i =0;
                gameController.updateObjectsLocations();
                for(GameObject gameObject:gameObjectList)
                {
                    imageList.get(i).setLayoutY(gameObject.getYlocation());
                    if (gameObject.isSliced()) {
                        imageList.get(i).setImage(SwingFXUtils.toFXImage(gameObject.getBufferedImages()[1], null));
                    }
                    i++;
                }
                objectPane.getChildren().setAll(imageList);
                //get slice region
                gameScene.setOnMouseDragged(e1 -> {
                    mouseX = e1.getX();
                    mouseY = e1.getY();
                    gameController.sliceObjects();
                });
            }
        };
        timer.start();

        // Change cursor image
        objectPane.setOnMousePressed(e ->{
            try {
                objectPane.setCursor(new ImageCursor(new Image(new FileInputStream("Images\\SHURIKEN.png"))));
            } catch (FileNotFoundException | NullPointerException f) {
                f.printStackTrace();
            }
        });
        objectPane.setOnMouseReleased(e ->{
            objectPane.setCursor(Cursor.DEFAULT);
        });

        //set layouts
        objectPane.getChildren().addAll(imageList);
        labelPane.getChildren().addAll(highScoreLabel, scoreLabel, timeLabel, livesLabel);
        gameLayout.getChildren().addAll(labelPane, objectPane);
        gameScene = new Scene(gameLayout, 600, 400);
        primaryStage.setScene(gameScene);
    }

    private Game() {}
    public static Game getInstance() {
        if (instance==null)
            instance = new Game();
        return instance;
    }

    public Scene getGameScene() { return gameScene; }

    public List<GameObject> getGameObjectList() { return gameObjectList; }

    public int getTimeSeconds() { return timeSeconds; }

    public int getTimeFrames() { return timeFrames; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public double getMouseX() { return mouseX; }

    public double getMouseY() { return mouseY; }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
