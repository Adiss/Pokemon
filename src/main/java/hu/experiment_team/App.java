package hu.experiment_team;

import hu.experiment_team.models.Trainer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

/**
 * Main class of the application.
 * @author Jakab �d�m
 * */
public class App extends Application {

    /**
     * This method will be called when the program start, launches the JavaFX application.
     * @param args Arguments of the program
     * */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pokemon");

        // RegistrationScene BEGIN

        VBox registrationRoot = new VBox();
        registrationRoot.setSpacing(5);
        registrationRoot.setPadding(new Insets(10, 10, 10, 10));

        Label registrationNameLabel = new Label("Username");
        Label registrationDisplayNameLabel = new Label("Display name");
        Label registrationPasswordLabel = new Label("Password");
        Label registrationPassword2Label = new Label("Password Again");
        Label registrationEmailLabel = new Label("E-mail");
        TextField registrationNameTextField = new TextField();
        TextField registrationDisplayNameTextField = new TextField();
        TextField registrationPasswordTextField = new TextField();
        TextField registrationPassword2TextField = new TextField();
        TextField registrationEmailTextField = new TextField();

        Button registrationSubmit = new Button("Submit");

        registrationRoot.getChildren().addAll(
                registrationNameLabel,
                registrationNameTextField,
                registrationDisplayNameLabel,
                registrationDisplayNameTextField,
                registrationPasswordLabel,
                registrationPasswordTextField,
                registrationPassword2Label,
                registrationPassword2TextField,
                registrationEmailLabel,
                registrationEmailTextField,
                registrationSubmit);

        Scene registrationScene = new Scene(registrationRoot, 500, 500, Color.WHITE);

        // RegistrationScene END

        // LoginScene BEGIN

        VBox loginRoot = new VBox();
        loginRoot.setSpacing(5);
        loginRoot.setPadding(new Insets(10, 10, 10, 10));

        Label loginNameLabel = new Label("Username");
        Label loginPasswordLabel = new Label("Password");
        Label loginErrorField = new Label("");
        TextField loginNameTextField = new TextField();
        TextField loginPasswordTextField = new TextField();

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Registration");
        HBox buttonHolder = new HBox();
        buttonHolder.setSpacing(10);
        buttonHolder.setPadding(new Insets(20, 20, 20, 20));
        buttonHolder.getChildren().addAll(loginButton, registerButton);

        loginButton.setOnAction(e -> {
            Trainer user = UserMethods.INSTANCE.login(loginNameTextField.getText(), loginPasswordTextField.getText());
            if((user != null) && (user.getOnline() == 1)){
                loginErrorField.setText("Sikeres Bejelentkezés!");
            } else
                loginErrorField.setText("Hibás felhasználónév vagy jelszó!");
        });
        registerButton.setOnAction(e -> {
            primaryStage.setScene(registrationScene);
        });

        loginRoot.getChildren().addAll(loginErrorField ,loginNameLabel, loginNameTextField, loginPasswordLabel, loginPasswordTextField, buttonHolder);

        Scene loginScene = new Scene(loginRoot, 500, 300, Color.WHITE);
        primaryStage.setScene(loginScene);

        registrationSubmit.setOnAction(e -> {
            List<String> errors = UserMethods.INSTANCE.register(registrationNameTextField.getText(), registrationDisplayNameTextField.getText(), registrationPasswordTextField.getText(), registrationPassword2TextField.getText(), registrationEmailTextField.getText());
            if (errors.get(0).equals("Sikeres regisztracio!"))
                primaryStage.setScene(loginScene);
            else
                for (String err : errors)
                    registrationRoot.getChildren().add(new Label(err));
        });

        // LoginScene END



        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
