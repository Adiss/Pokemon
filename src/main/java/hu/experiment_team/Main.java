package hu.experiment_team;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage.setTitle("Pokémon");
        stage.setWidth(400);
        stage.setHeight(310);

        // Initialize buttons
        Button btnFight = new Button("Fight");
        Button btnBag = new Button("Bag");
        Button btnRun = new Button("Run");
        Button btnPokemon = new Button("Pokemon");
        Button btnMove1 = new Button("Move1");
        Button btnMove2 = new Button("Move2");
        Button btnMove3 = new Button("Move3");
        Button btnMove4 = new Button("Move4");

        // Initialize screen components
        VBox root = new VBox();
        HBox upperScreen = new HBox();
        HBox lowerScreen = new HBox();
        VBox lowerScreenMoveButtons = new VBox();
        VBox lowerScreenActionButtons = new VBox();

        HBox movesTopHolder = new HBox();
        HBox movesBottomHolder = new HBox();
        HBox menuTopImage = new HBox();
        HBox actionTopHolder = new HBox();
        HBox actionBottomHolder = new HBox();

        upperScreen.getStyleClass().add("upperScreen");
        upperScreen.setPrefWidth(400);
        upperScreen.setPrefHeight(238);

        menuTopImage.getStyleClass().add("menuTopImage");

        btnFight.getStyleClass().add("actionButton");
        btnBag.getStyleClass().add("actionButton");
        btnRun.getStyleClass().add("actionButton");
        btnPokemon.getStyleClass().add("actionButton");
        btnMove1.getStyleClass().add("moveButton");
        btnMove2.getStyleClass().add("moveButton");
        btnMove3.getStyleClass().add("moveButton");
        btnMove4.getStyleClass().add("moveButton");

        movesTopHolder.getChildren().addAll(btnMove1, btnMove2);
        movesBottomHolder.getChildren().addAll(btnMove3, btnMove4);

        actionTopHolder.getChildren().addAll(btnFight, btnBag);
        actionBottomHolder.getChildren().addAll(btnPokemon, btnRun);

        // Action gombok hozzáadás a Hboxhoz
        lowerScreenActionButtons.getChildren().addAll(menuTopImage, actionTopHolder, actionBottomHolder);
        // Move gombok hozzáadása a Hboxhoz
        lowerScreenMoveButtons.getChildren().addAll(movesTopHolder, movesBottomHolder);
        // Gombos Hboxok hozzáadása az alső Hbox-hoz
        lowerScreen.getStyleClass().add("lowerScreen");
        lowerScreen.getChildren().addAll(lowerScreenMoveButtons, lowerScreenActionButtons);
        // Alsó és felső Hbox hozzáadása a roothoz
        root.getChildren().addAll(upperScreen, lowerScreen);

        Scene primaryScene = new Scene(root, 400, 300);
        primaryScene.getStylesheets().add("css/master.css");
        stage.setScene(primaryScene);

        stage.setResizable(false);
        stage.show();
    }

}
