package com.example.hyplayer;

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

public class HelloController {

    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginMsg;
    @FXML
    private Label errorMsg;
    @FXML
    private Label emailMsg;
    @FXML
    protected void handleLogin() {
        try {
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("select email from users where username =? and password=?");
            ps.setString(1, usernameField.getText());
            ps.setString(2, passwordField.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.getFetchSize() == 0){
                errorMsg.setText("Błąd logowania(złe hasło lub login)");
            }
            while(rs.next()){
                homePanel();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void homePanel() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home-panel.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.show();
        Stage curentStage = (Stage) loginButton.getScene().getWindow(); //Current Stage
        curentStage.close(); // Closing this window
    }
    @FXML
   private void registrationButtonAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registration-window.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.show();
        Stage curentStage = (Stage) loginButton.getScene().getWindow(); //Current Stage
        curentStage.close(); // Closing this window
    }
}