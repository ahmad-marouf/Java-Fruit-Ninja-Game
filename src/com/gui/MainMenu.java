package com.gui;

import com.players.Players;
import com.levels.Difficulty;
import com.levels.Easy;
import com.levels.Hard;
import com.levels.Medium;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainMenu extends Application {


    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox menuButtons = new VBox(20);
        Scene menuScene = new Scene(menuButtons, 600, 400);
        try {
            BackgroundImage backgroundImage = new BackgroundImage(new Image((new FileInputStream("Images\\BACKGROUND2.jpg")), 600, 400, false, true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            menuButtons.setBackground(new Background(backgroundImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Label mainMenu = new Label("Main Menu ");
        mainMenu.setFont(new Font(48));
        mainMenu.setTextFill(Color.WHITE);
        Button play = new Button("PLAY");
        Button HighScores = new Button("HIGH SCORES");
        Button Instructions = new Button("Instructions");
        Button Exit = new Button("EXIT");

        ChoiceBox<String> difficultySelection = new ChoiceBox<>();
        difficultySelection.getItems().addAll("EASY", "MEDIUM", "HARD");
        difficultySelection.setValue("MEDIUM");
        HBox gameStart = new HBox(20);
        gameStart.setAlignment(Pos.CENTER);
        gameStart.getChildren().addAll(play, difficultySelection);

        Game game = Game.getInstance();

        VBox playerBox = getPlayers();

        ScoreBoard scoreBoard = new ScoreBoard();
        Instructions instructions = new Instructions();

        play.setOnAction(e -> {
            Difficulty difficulty = getDifficulty(difficultySelection.getSelectionModel().getSelectedItem());
            game.setPrimaryStage(primaryStage);
            game.setMainMenuScene(menuScene);
            game.getGameState().setDifficulty(difficulty);
            if (game.getGameState().getPlayer() == null)
                game.getGameState().setPlayer("Guest");
            game.startGame();
        });
         HighScores.setOnAction(e->{
             try {
                 scoreBoard.start(primaryStage);
             } catch (Exception exception) {
                 exception.printStackTrace();
             }
         });
         Instructions.setOnAction(e->{
             try {
                 instructions.start(primaryStage);
             } catch (Exception exception) {
                 exception.printStackTrace();
             }
         });

        Exit.setOnAction(e -> {
            primaryStage.close();
        });
        menuButtons.getChildren().addAll(mainMenu, gameStart, playerBox, HighScores,Instructions, Exit);
        menuButtons.setAlignment(Pos.CENTER);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private Difficulty getDifficulty(String difficultyValue) {
        if (difficultyValue.equalsIgnoreCase("EASY"))
            return new Easy();
        else if (difficultyValue.equalsIgnoreCase("MEDIUM"))
            return new Medium();
        else if (difficultyValue.equalsIgnoreCase("HARD"))
            return new Hard();
        else return null;
    }

    private VBox getPlayers() throws JAXBException {
        VBox playerBox = new VBox(20);
        HBox displayPlayers = new HBox(20);
        Label playerLabel = new Label("Player : ");
        playerLabel.setTextFill(Color.WHITE);
        playerLabel.setFont(new Font(20));
        ComboBox<String> selectPlayer = new ComboBox<>();
        selectPlayer.getItems().addAll("Guest", "Create player");
        selectPlayer.setValue("Guest");

        HBox newPlayerBox = new HBox(20);
        Button addPlayer = new Button("Add");
        TextField newPlayerName = new TextField();
        newPlayerName.setPromptText("Enter player name");
        newPlayerBox.getChildren().addAll(newPlayerName, addPlayer);
        newPlayerBox.setAlignment(Pos.CENTER);

        Players players = new Players();
        players.load();

        for (String player : players.getPlayerList())
            selectPlayer.getItems().add(player);

        selectPlayer.setOnAction(e ->{
            if (selectPlayer.getSelectionModel().getSelectedItem().equalsIgnoreCase("Create player"))
                playerBox.getChildren().add(newPlayerBox);
            else {
                Game.getInstance().getGameState().setPlayer(selectPlayer.getSelectionModel().getSelectedItem());
                playerBox.getChildren().remove(newPlayerBox);
            }
        });

        addPlayer.setOnAction(e ->{
            try {
                players.add(newPlayerName.getText());
                selectPlayer.getItems().add(newPlayerName.getText());
                selectPlayer.setValue(newPlayerName.getText());
                Game.getInstance().getGameState().setPlayer(newPlayerName.getText());
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
            playerBox.getChildren().remove(newPlayerBox);
        });

        displayPlayers.getChildren().addAll(playerLabel, selectPlayer);
        displayPlayers.setAlignment(Pos.CENTER);
        playerBox.getChildren().add(displayPlayers);
        playerBox.setAlignment(Pos.CENTER);
        return playerBox;
    }
}