package com.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Instructions {


    public void start(Stage primaryStage) throws JAXBException, FileNotFoundException {
        Stage window=new Stage();

        window.setTitle("Instructions");
        Button back=new Button("Back");

        Image Apple = new Image(new FileInputStream("Images\\APPLE.png"));
        ImageView apple = new ImageView(Apple);

        Image Orange = new Image(new FileInputStream("Images\\ORANGE.png"));
        ImageView orang = new ImageView(Orange);

        Image Pineapple = new Image(new FileInputStream("Images\\PINEAPPLE.png"));
        ImageView pineapple = new ImageView(Pineapple);

        Image Strawberry = new Image(new FileInputStream("Images\\STRAWBERRY.png"));
        ImageView strawberry = new ImageView(Strawberry);

        Image Watermelon = new Image(new FileInputStream("Images\\WATERMELON.png"));
        ImageView watermelon = new ImageView(Watermelon);

        Image DangerousBomb = new Image(new FileInputStream("Images\\DANGEROUS.png"));
        ImageView dangerousBomb = new ImageView(DangerousBomb);

        Image FatalBomb = new Image(new FileInputStream("Images\\FATAL.png"));
        ImageView fatalBomb = new ImageView(FatalBomb);

        Image DragonFruit = new Image(new FileInputStream("Images\\DRAGONFRUIT.png"));
        ImageView dragonFruit = new ImageView(DragonFruit);

        Image StarFruit = new Image(new FileInputStream("Images\\STARFRUIT.png"));
        ImageView starFruit = new ImageView(StarFruit);



        Label NormalFruits = new Label("slicing it you get 20 points");
        Label ExtraPointsFruits = new Label("slicing it you get 50 points");
        Label SpecialFruits = new Label("slicing StarFruit you get extra life \n slicing DragonFruit makes slice all fruits");
        Label Bombs = new Label("slicing the FatalBomb (red) it end the game immediately \n slicing the DangerousBomb ( blue) it removes a life");


        GridPane grid = new GridPane();
        GridPane.setColumnSpan(NormalFruits,3);
        grid.add(NormalFruits, 0, 1);
        GridPane.setColumnSpan(ExtraPointsFruits,3);
        grid.add(ExtraPointsFruits, 0, 3);
        GridPane.setColumnSpan(SpecialFruits,3);
        grid.add(SpecialFruits, 0, 5);
        GridPane.setColumnSpan(Bombs,3);
        grid.add(Bombs, 0, 7);
        grid.add(apple, 0, 0);
        grid.add(orang, 1, 0);
        grid.add(watermelon, 2, 0);
        grid.add(pineapple, 0, 2);
        grid.add(strawberry, 1, 2);
        grid.add(dangerousBomb, 0, 6);
        grid.add(fatalBomb, 1, 6);
        grid.add(starFruit, 0, 4);
        grid.add(dragonFruit, 1, 4);
        grid.add(back, 0, 9);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10,10,10,10));

        MainMenu main =new MainMenu();
        back.setOnAction(e->{
            try {
                main.start(primaryStage);
                window.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });



        Scene scene = new Scene(grid,600,600);
        window.setScene(scene);
        window.show();
    }
}
