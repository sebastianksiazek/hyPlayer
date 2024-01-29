package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.hyplayer.RegistrationWindowController;

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
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            RegistrationWindowController hashPasswordSHA1 = new RegistrationWindowController();
            String hashedPassword = RegistrationWindowController.hashPasswordSHA1(password);
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("select * from users where username =? and password=?");
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ResultSet rs = ps.executeQuery();
            if(rs.getFetchSize() == 0){
                errorMsg.setText("Błąd logowania(złe hasło lub login)");
            }
            while(rs.next()){
                CurrentUserData currentUserData = CurrentUserData.getInstance();
                currentUserData.setUserId(rs.getInt("user_id"));
                System.out.println("User id: " + currentUserData.getUserId());
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
        newStage.setResizable(false);
        newStage.show();
        Stage curentStage = (Stage) loginButton.getScene().getWindow(); //Current Stage
        curentStage.close(); // Closing this window
    }
    @FXML
   private void registrationButtonAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registration-window.fxml"));
        Stage currentStage = (Stage) loginButton.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }
}