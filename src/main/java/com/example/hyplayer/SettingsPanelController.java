package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class SettingsPanelController implements Initializable {
    @FXML
    private Label idLabel;
    @FXML
    private Button userSettings;
    @FXML
    private Label errorMsg;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CurrentUserData currentUserData = CurrentUserData.getInstance();
        idLabel.setText(String.valueOf(currentUserData.getUserId()));
        System.out.println("User id: " + currentUserData.getUserId());
    }

    @FXML
    private void userSettingsAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("user-settings-panel.fxml"));
        Stage currentStage = (Stage) userSettings.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }
    public void isUserAnAdmin(String userId) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String usernameQuery = "SELECT * FROM users WHERE user_id=?";
            try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                usernameStatement.setString(1, userId);
                ResultSet rs = usernameStatement.executeQuery();
                while(rs.next()){
                    if(rs.getInt("user_type")==0){
                        errorMsg.setText("Nie jesteś adminem!");
                    }
                    if(rs.getInt("user_type")==1){
                        Parent root = FXMLLoader.load(getClass().getResource("admin-panel.fxml"));
                        Stage currentStage = (Stage) userSettings.getScene().getWindow(); //Current Stage
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adminButtonAction() throws IOException {
        isUserAnAdmin(String.valueOf(CurrentUserData.getInstance().getUserId()));
    }


    public void statuteAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("statute-panel.fxml"));
        Stage currentStage = (Stage) userSettings.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }
    public void purchaseHistoryAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("purchase-history-panel.fxml"));
        Stage currentStage = (Stage) userSettings.getScene().getWindow(); //Current Stage
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
