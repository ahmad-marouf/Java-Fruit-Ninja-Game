package com.gui;

import com.players.Players;
import com.players.Score;
import com.players.Scores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;

public class ScoreBoard {
    public void start(Stage primaryStage) throws JAXBException  {
        Stage window=new Stage();

        Scores scores = new Scores();
        scores.load();

        TableView<Score> table;


        window.setTitle("High Scores");
        Button back=new Button("Back");

        TableColumn<Score, Integer> scoreColumn = new TableColumn<Score, Integer>("High Scores");
        scoreColumn.setMinWidth(150);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<Score, String> nameColumn = new TableColumn<Score, String>("Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Score, String> difficultyColumn = new TableColumn<Score, String>("Difficulty");
        difficultyColumn.setMinWidth(150);
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        table = new TableView();
        ObservableList<Score> scoreList = FXCollections.observableArrayList();
        for (Score score : scores.getScoresList()){
            scoreList.add(score);
         }

        table.setItems(scoreList);
        table.getColumns().addAll(nameColumn,difficultyColumn,scoreColumn);

        MainMenu main =new MainMenu();
        back.setOnAction(e->{
            try {
                main.start(primaryStage);
                window.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(table,back);

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.show();
    }
}
