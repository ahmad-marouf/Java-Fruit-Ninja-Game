package com.gui;

import com.controllers.GameController;
import com.controllers.GameObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


import com.controllers.GameState;
import com.controllers.Scores;
import com.levels.Difficulty;
import com.levels.Easy;
import com.levels.Hard;
import com.levels.Medium;
import com.objects.Object;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.plugin.util.UIUtil;

import javax.xml.bind.JAXBException;

public class Game {

    private Stage primaryStage;
    private Scene mainMenuScene;
    private Scene gameScene;
    private GameController gameController;
    private StackPane gameLayout;
    private Pane objectPane;
    private HBox livesDisplay;
    private Timeline timeCounter;
    private Timeline timeline;
    private AnimationTimer timer;
    private List<ImageView> imageList;
//    private List<GameObject> gameObjectList;
//    private int timeSeconds;
//    private int timeFrames;
//    private int score;
//    private int lives;
    private double mouseX;
    private double mouseY;
//    private double spawnRate;
    private Difficulty difficulty;
    private GameState gameState;
    private static Game instance;


    public void startGame() {
        gameController = new GameController();
        gameLayout = new StackPane();
        Pane labelPane = new Pane();
        Label highScoreLabel = new Label("Highest Score: ");
        highScoreLabel.setTextFill(Color.WHITE);
        highScoreLabel.setLayoutY(20);
        highScoreLabel.setLayoutX(10);
        Label scoreLabel = new Label("Current Score: ");
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutY(45);
        scoreLabel.setLayoutX(10);
        Label timeLabel = new Label("Time: ");
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setLayoutY(20);
        timeLabel.setLayoutX(380);
        livesDisplay = new HBox();
        Label livesLabel = new Label("Lives Remaining: ");
        livesLabel.setTextFill(Color.WHITE);
        livesDisplay.setLayoutY(40);
        livesDisplay.setLayoutX(380);
        livesDisplay.getChildren().add(livesLabel);
        // Add lives images to display
        displayLives(livesDisplay);
        //Load previous objects' images
        restoreImages();
        //restore difficulty
        restoreDifficulty();

        timeCounter = new Timeline(new KeyFrame(Duration.millis(1000), e1 -> {
            gameState.setTimeSeconds(gameState.getTimeSeconds() + 1);
            timeLabel.setText("Time: "+ gameState.getTimeSeconds());
        }));
        timeCounter.setCycleCount(Timeline.INDEFINITE);
        timeCounter.play();

        getGameState().setSpawnRate(difficulty.getSpawnRate());
        timeline = new Timeline(new KeyFrame(Duration.millis(gameState.getSpawnRate()), e -> {
            GameObject gameObject = gameController.createGameObject();
            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(gameObject.getBufferedImages()[0], null));
            imageView.setLayoutX(gameObject.getXlocation());
            imageView.setLayoutY(gameObject.getYlocation());
            imageList.add(imageView);
            gameState.getGameObjectList().add((Object) gameObject);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameState.setTimeFrames(gameState.getTimeFrames() + 1);
                scoreLabel.setText("Current Score: " + gameState.getScore());
                //move imageViews on screen
                gameController.updateObjectsLocations();
                if (imageList.size() > gameState.getGameObjectList().size())
                    imageList.remove(0);
                int i =0;
                for(GameObject gameObject:gameState.getGameObjectList())
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

        try {
            BackgroundImage backgroundImage= new BackgroundImage(new Image((new FileInputStream("Images\\BACKGROUND2.jpg")),600,400,false,true),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            gameLayout.setBackground(new Background(backgroundImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //set layouts
        objectPane.getChildren().addAll(imageList);
        labelPane.getChildren().addAll(highScoreLabel, scoreLabel, timeLabel, livesDisplay);
        gameLayout.getChildren().addAll(labelPane, objectPane);
        gameScene = new Scene(gameLayout, 600, 400);
        primaryStage.setScene(gameScene);
    }

    private void restoreDifficulty() {
        if (this.difficulty == null) {
            if (gameState.getDifficulty().equalsIgnoreCase("EASY"))
                difficulty = new Easy();
            else if (gameState.getDifficulty().equalsIgnoreCase("MEDIUM"))
                difficulty = new Medium();
            else if (gameState.getDifficulty().equalsIgnoreCase("HARD"))
                difficulty = new Hard();
        }
    }

    private void restoreImages() {
        if (gameState.getGameObjectList() != null) {
            for (GameObject gameObject : gameState.getGameObjectList()){
                ImageView imageView = new ImageView(SwingFXUtils.toFXImage(gameObject.getBufferedImages()[0], null));
                imageView.setLayoutX(gameObject.getXlocation());
                imageView.setLayoutY(gameObject.getYlocation());
                imageList.add(imageView);
            }
        }
    }

    public void removeOffScreenObject(GameObject gameObject) {
        gameState.getGameObjectList().remove(gameObject);
        imageList.remove(0);
    }

    public void removeLife(HBox livesDisplay) {
        gameState.setLives(gameState.getLives() - 1);
        livesDisplay.getChildren().remove(livesDisplay.getChildren().size()-1);
        if (gameState.getLives() == 0)
            gameOver();
        }


    private void displayLives(HBox livesDisplay) {
        try {
            ImageView[] livesArray = new ImageView[3];
            livesArray[0] = new ImageView(new Image(new FileInputStream("Images\\Life.png")));
            livesArray[1] = new ImageView(new Image(new FileInputStream("Images\\Life.png")));
            livesArray[2] = new ImageView(new Image(new FileInputStream("Images\\Life.png")));
            for (int i = 0; i < gameState.getLives(); i++)
                livesDisplay.getChildren().add(livesArray[i]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void gameOver() {
        VBox gameOver = new VBox(20);
        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(new Font(36));
        gameOverLabel.setTextFill(Color.WHITE);
        Button restartGameButton = new Button("Restart Game");
        Button exitButton = new Button("Exit to main menu");

        objectPane.setOpacity(0.2);
        timer.stop();
        timeline.stop();
        timeCounter.stop();

        restartGameButton.setOnAction(e -> {
            objectPane.setOpacity(1);
            gameLayout.getChildren().remove(gameOver);
            gameController.resetGame();
            startGame();
        });

        exitButton.setOnAction(e -> {
            gameController.resetGame();
            primaryStage.setScene(mainMenuScene);
        });

        gameOver.setAlignment(Pos.CENTER);
        gameOver.getChildren().addAll(gameOverLabel, restartGameButton, exitButton);
        gameLayout.getChildren().add(gameOver);
    }

    private Game() {
        imageList = new ArrayList<>();
//        gameObjectList = new CopyOnWriteArrayList<>();
        objectPane = new Pane();
//        difficulty = new Medium();
//        spawnRate = difficulty.getSpawnRate();
//        timeSeconds = 0;
//        timeFrames = 0;
//        lives = 3;
        gameState = new GameState(new CopyOnWriteArrayList<>(), 0,0,0,3);
    }
    public static Game getInstance() {
        if (instance==null)
            instance = new Game();
        return instance;
    }

    public Scene getGameScene() { return gameScene; }

//    public List<GameObject> getGameObjectList() { return gameObjectList; }

    public void setImageList(List<ImageView> imageList) { this.imageList = imageList; }

//    public void setGameObjectList(List<GameObject> gameObjectList) { this.gameObjectList = gameObjectList; }

//    public void setTimeSeconds(int timeSeconds) { this.timeSeconds = timeSeconds; }

//    public void setTimeFrames(int timeFrames) { this.timeFrames = timeFrames; }

//    public int getTimeFrames() { return timeFrames; }

//    public int getScore() { return score; }

//    public void setScore(int score) { this.score = score; }

    public double getMouseX() { return mouseX; }

    public double getMouseY() { return mouseY; }

    public Difficulty getDifficulty() { return difficulty; }

    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

//    public void setLives(int lives) { this.lives = lives; }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public void setMainMenuScene(Scene mainMenuScene) { this.mainMenuScene = mainMenuScene; }

    public HBox getLivesDisplay() { return livesDisplay; }

    public GameState getGameState() { return gameState; }

    public void setGameState(GameState gameState) { this.gameState = gameState; }
}
