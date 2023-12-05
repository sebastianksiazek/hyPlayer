package com.example.hypelay;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class MusicPlayer {

    @FXML
    private Label errorMsg;

    @FXML
    private Label homeName;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label qstnRegistration;

    @FXML
    private Button registrationButton;
    @FXML
    protected void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    homePanel();
                } else {
                    errorMsg.setText("Błąd logowania (zły login lub hasło)");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    private void homePanel() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home-panel.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.show();
    }
    public void registrationWindowBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registration-window.fxml"));

        Scene scene = new Scene(root);
        Stage loginStage = new Stage();
        loginStage.setTitle("hypelay");
        loginStage.setScene(scene);
        loginStage.show();
    }

}
