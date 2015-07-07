package hu.experiment_team;

import hu.experiment_team.battleAI.BattleAI;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){

        Trainer t1 = UserMethods.INSTANCE.login("adiss", "422341");
        Trainer t2 = UserMethods.INSTANCE.login("teszt", "teszt");

        t1.chooseRandomPartyPokemons();
        t2.chooseRandomPartyPokemons();

        Pokemon t1Choosen = t1.getPartyPokemons().get(0);
        Pokemon t2Choosen = t2.getPartyPokemons().get(0);

        stage.setTitle("Pokémon");
        stage.setWidth(800);
        stage.setHeight(482);

        // Initialize buttons
        Button btnFight = new Button("Fight");
        Button btnBag = new Button("Bag");
        Button btnRun = new Button("Run");
        Button btnPokemon = new Button("Pokemon");
        Button btnMove1;
        Button btnMove2;
        Button btnMove3;
        Button btnMove4;
        if(t1Choosen.getMove1() != null) btnMove1 = new Button(t1Choosen.getMove1().getDisplayName()); else btnMove1 = new Button("");
        if(t1Choosen.getMove2() != null) btnMove2 = new Button(t1Choosen.getMove2().getDisplayName()); else btnMove2 = new Button("");
        if(t1Choosen.getMove3() != null) btnMove3 = new Button(t1Choosen.getMove3().getDisplayName()); else btnMove3 = new Button("");
        if(t1Choosen.getMove4() != null) btnMove4 = new Button(t1Choosen.getMove4().getDisplayName()); else btnMove4 = new Button("");

        // Initialize screen components
        VBox root = new VBox();
        AnchorPane upperScreen = new AnchorPane();
        HBox lowerScreen = new HBox();
        VBox lowerScreenMoveButtons = new VBox();
        VBox lowerScreenActionButtons = new VBox();

        HBox movesTopHolder = new HBox();
        HBox movesBottomHolder = new HBox();
        HBox menuTopImage = new HBox();
        HBox actionTopHolder = new HBox();
        HBox actionBottomHolder = new HBox();

        upperScreen.getStyleClass().add("upperScreen");
        upperScreen.setPrefWidth(800);
        upperScreen.setPrefHeight(400);

        // Initialize pokemons

        HBox myPokemonContainer = new HBox();
        myPokemonContainer.getStyleClass().add("myPokemonContainer");
        HBox opponentPokemonContainer = new HBox();
        opponentPokemonContainer.getStyleClass().add("opponentPokemonContainer");

        ImageView myPokemon = new ImageView(new Image("images/Battlers/"+Utility.INSTANCE.getImageName(t1Choosen.getId())+"b.gif"));
        ImageView opponentPokemon = new ImageView(new Image("images/Battlers/"+Utility.INSTANCE.getImageName(t2Choosen.getId())+".gif"));

        myPokemonContainer.getChildren().add(myPokemon);
        opponentPokemonContainer.getChildren().add(opponentPokemon);

        /* RESIZE POKEMONS
        myPokemon.fitWidthProperty().bind(myPokemonContainer.widthProperty());
        myPokemon.fitHeightProperty().bind(myPokemonContainer.heightProperty());
        opponentPokemon.fitWidthProperty().bind(opponentPokemonContainer.widthProperty());
        opponentPokemon.fitHeightProperty().bind(opponentPokemonContainer.heightProperty());
        */


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

        AnchorPane.setBottomAnchor(myPokemonContainer, 0.0);
        AnchorPane.setLeftAnchor(myPokemonContainer, 0.0);
        AnchorPane.setTopAnchor(opponentPokemonContainer, 150.0);
        AnchorPane.setRightAnchor(opponentPokemonContainer, 150.0);
        upperScreen.getChildren().addAll(opponentPokemonContainer, myPokemonContainer);

        // Action gombok hozzáadás a Hboxhoz
        lowerScreenActionButtons.getChildren().addAll(menuTopImage, actionTopHolder, actionBottomHolder);
        // Move gombok hozzáadása a Hboxhoz
        lowerScreenMoveButtons.getChildren().addAll(movesTopHolder, movesBottomHolder);
        // Gombos Hboxok hozzáadása az alső Hbox-hoz
        lowerScreen.getStyleClass().add("lowerScreen");
        lowerScreen.getChildren().addAll(lowerScreenMoveButtons, lowerScreenActionButtons);
        // Alsó és felső Hbox hozzáadása a roothoz
        root.getChildren().addAll(upperScreen, lowerScreen);

        Scene primaryScene = new Scene(root, 800, 482);
        primaryScene.getStylesheets().add("css/master.css");
        stage.setScene(primaryScene);

        stage.setResizable(false);
        stage.show();

        /*
                if(t1Choosen.getMove1() != null){
            btnMove1.setOnAction(e -> {
                t1Choosen.dealDamage(t2Choosen, t1Choosen.getMove1());
                Move t2ChoosenMove = BattleAI.INSTANCE.calculateNextMove(t2Choosen, t1Choosen);
                t2Choosen.dealDamage(t1Choosen, t2ChoosenMove);
                if(t1Choosen.getHp() <= 0){
                    myPokemon.setOpacity(0.1);
                    System.out.println("Your pokemon has been fainted!" + '\n' + '\n');
                    t1.setActivePokemons(t1.getActivePokemons()-1);
                } else if(t2Choosen.getHp() <= 0) {
                    opponentPokemon.setOpacity(0.1);
                    System.out.println("Enemy pokemon has been fainted!" + '\n' + '\n');
                    t2.setActivePokemons(t2.getActivePokemons()-1);
                }
            });
        }
        if(t1Choosen.getMove2() != null){
            btnMove2.setOnAction(e -> {
                t1Choosen.dealDamage(t2Choosen, t1Choosen.getMove2());
                Move t2ChoosenMove = BattleAI.INSTANCE.calculateNextMove(t2Choosen, t1Choosen);
                t2Choosen.dealDamage(t1Choosen, t2ChoosenMove);
                if(t1Choosen.getHp() <= 0){
                    myPokemon.setOpacity(0.1);
                    System.out.println("Your pokemon has been fainted!" + '\n' + '\n');
                    t1.setActivePokemons(t1.getActivePokemons()-1);
                } else if(t2Choosen.getHp() <= 0) {
                    opponentPokemon.setOpacity(0.1);
                    System.out.println("Enemy pokemon has been fainted!" + '\n' + '\n');
                    t2.setActivePokemons(t2.getActivePokemons()-1);
                }
            });
        }
        if(t1Choosen.getMove3() != null){
            btnMove3.setOnAction(e -> {
                t1Choosen.dealDamage(t2Choosen, t1Choosen.getMove3());
                Move t2ChoosenMove = BattleAI.INSTANCE.calculateNextMove(t2Choosen, t1Choosen);
                t2Choosen.dealDamage(t1Choosen, t2ChoosenMove);
                if(t1Choosen.getHp() <= 0){
                    myPokemon.setOpacity(0.1);
                    System.out.println("Your pokemon has been fainted!" + '\n' + '\n');
                    t1.setActivePokemons(t1.getActivePokemons()-1);
                } else if(t2Choosen.getHp() <= 0) {
                    opponentPokemon.setOpacity(0.1);
                    System.out.println("Enemy pokemon has been fainted!" + '\n' + '\n');
                    t2.setActivePokemons(t2.getActivePokemons()-1);
                }
            });
        }
        if(t1Choosen.getMove4() != null){
            btnMove4.setOnAction(e -> {
                t1Choosen.dealDamage(t2Choosen, t1Choosen.getMove4());
                Move t2ChoosenMove = BattleAI.INSTANCE.calculateNextMove(t2Choosen, t1Choosen);
                t2Choosen.dealDamage(t1Choosen, t2ChoosenMove);
                if(t1Choosen.getHp() <= 0){
                    myPokemon.setOpacity(0.1);
                    System.out.println("Your pokemon has been fainted!" + '\n' + '\n');
                    t1.setActivePokemons(t1.getActivePokemons()-1);
                } else if(t2Choosen.getHp() <= 0) {
                    opponentPokemon.setOpacity(0.1);
                    System.out.println("Enemy pokemon has been fainted!" + '\n' + '\n');
                    t2.setActivePokemons(t2.getActivePokemons()-1);
                }
            });
        }
        * */

    }

}
