package com.gui;

import com.controllers.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


import com.levels.Difficulty;
import com.levels.Easy;
import com.levels.Hard;
import com.levels.Medium;
import com.objects.Object;
import com.players.Score;
import com.players.Scores;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private double mouseX;
    private double mouseY;
    private Difficulty difficulty;
    private Scores scores;
    private GameState gameState;
    MediaPlayer mediaPlayer;
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
        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutY(10); pauseButton.setLayoutX(200);
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
        //set objectPane
        objectPane.setOpacity(1);
        objectPane.setMaxHeight(340);
        //load scores
        scores = new Scores();
        try {
            scores.load();
            highScoreLabel.setText("Highest Score: " + scores.getHighScore());
        } catch (JAXBException | NullPointerException e) {
            e.printStackTrace();
        }
        //play music
        String mediaFile = "Sounds\\game music.mp3";
        Media sliceSound = new Media(new File(mediaFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sliceSound);
        mediaPlayer.play();


        timeCounter = new Timeline(new KeyFrame(Duration.millis(1000), e1 -> {
            gameState.setTimeSeconds(gameState.getTimeSeconds() + 1);
            timeLabel.setText("Time: "+ gameState.getTimeSeconds());
        }));
        timeCounter.setCycleCount(Timeline.INDEFINITE);
        timeCounter.play();

        // timeline to throw objects
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

        //timer to move objects and check conditions
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

        gameLayout.setOnMouseEntered(e ->{
            pauseButton.setOnAction(e1 -> {
                timeline.pause();
                timer.stop();
                objectPane.setOpacity(0.5);
                VBox pauseMenu = pauseGame();
                gameLayout.getChildren().add(pauseMenu);
            });
        });

        //set layouts
        objectPane.getChildren().addAll(imageList);
        labelPane.getChildren().addAll(highScoreLabel, scoreLabel, timeLabel, livesDisplay, pauseButton);
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

    public void removeLife() {
        gameState.setLives(gameState.getLives() - 1);
        livesDisplay.getChildren().remove(livesDisplay.getChildren().size()-1);
        if (gameState.getLives() == 0)
            gameOver();
    }

    public void addLife() throws FileNotFoundException {
        if (gameState.getLives() < 3){
            gameState.setLives(gameState.getLives() + 1);
            livesDisplay.getChildren().add(new ImageView(new Image(new FileInputStream("Images\\Life.png"))));
        }
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

        objectPane.setOpacity(0.5);
        timer.stop();
        timeline.stop();
        timeCounter.stop();
        mediaPlayer.stop();
        Score score = new Score();
        score.setScore(gameState.getScore());
        score.setName(gameState.getPlayer());
        score.setDifficulty(gameState.getDifficulty());


        try {
            scores.load();
            scores.add(score);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

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

    private VBox pauseGame() {
        VBox pauseMenu = new VBox(20);
        Label pauseLabel = new Label("Paused");
        pauseLabel.setTextFill(Color.WHITE);
        pauseLabel.setFont(new Font(24));
        Button resumeGameButton = new Button("Resume");
        Button exitButton = new Button("Exit to main menu");

        resumeGameButton.setOnAction(e -> {
            timeline.play();
            timer.start();
            gameLayout.getChildren().remove(gameLayout.getChildren().size()-1);
            objectPane.setOpacity(1);
        });

        exitButton.setOnAction(e ->{
            mediaPlayer.stop();
            gameController.saveGame();
            gameController.resetGame();
            primaryStage.setScene(mainMenuScene);
        });

        pauseMenu.setAlignment(Pos.CENTER);
        pauseMenu.getChildren().addAll(pauseLabel, resumeGameButton, exitButton);

        return pauseMenu;
    }

    private Game() {
        imageList = new ArrayList<>();
        objectPane = new Pane();
        gameState = new GameState(new CopyOnWriteArrayList<>(), 0,0,0,3);
    }
    public static Game getInstance() {
        if (instance==null)
            instance = new Game();
        return instance;
    }

    public Scene getGameScene() { return gameScene; }


    public void setImageList(List<ImageView> imageList) { this.imageList = imageList; }


    public double getMouseX() { return mouseX; }

    public double getMouseY() { return mouseY; }

    public Difficulty getDifficulty() { return difficulty; }

    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public void setMainMenuScene(Scene mainMenuScene) { this.mainMenuScene = mainMenuScene; }

    public GameState getGameState() { return gameState; }

    public void setGameState(GameState gameState) { this.gameState = gameState; }

}
