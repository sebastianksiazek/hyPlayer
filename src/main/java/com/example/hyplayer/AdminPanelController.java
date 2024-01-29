package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPanelController {
    @FXML
    private TextField userNameField;
    @FXML
    private TextField editUserNameField;
    @FXML
    private Button grantAdmin;
    @FXML
    private Button eraseAdmin;
    @FXML
    private Label errorMsg;
    @FXML
    private TextField songId;


    public Boolean isUsernameInDatabase(String userId) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String usernameQuery = "SELECT * FROM users WHERE user_id=?";
            try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                usernameStatement.setString(1, userId);
                ResultSet rs = usernameStatement.executeQuery();
                if(rs.next()){
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void grantAdminAction() {
        CurrentUserData currentUserData = CurrentUserData.getInstance();
        String userId = String.valueOf(currentUserData.getUserId());
        String userName = userNameField.getText();
        if (userName.equals(userId)) {
            errorMsg.setText("Nie można nadać uprawnień administratora samemu sobie!");
        } else {
            if (isUsernameInDatabase(userName)) {
                try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
                    String usernameQuery = "UPDATE users SET user_type=1 WHERE user_id=?";
                    try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                        usernameStatement.setString(1, userName);
                        usernameStatement.executeUpdate();
                        errorMsg.setText("Użytkownik " + userName + " został administratorem!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                errorMsg.setText("Użytkownik " + userName + " nie istnieje!");
            }
        }
    }
    public boolean isOrginalAdmin(String userId) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String usernameQuery = "SELECT * FROM users WHERE user_id=?";
            try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                usernameStatement.setString(1, userId);
                ResultSet rs = usernameStatement.executeQuery();
                rs.next();
                    if (rs.getInt("orginal_admin") == 1) {
                        return true;
                    } else {
                        return false;
                    }

            }
        } catch (SQLException e) {}
        return false;
    }
        public void eraseAdminAction(){
        CurrentUserData currentUserData = CurrentUserData.getInstance();
        String userId = String.valueOf(currentUserData.getUserId());
        String userName = userNameField.getText();
        if (userName.equals(userId)) {
            errorMsg.setText("Nie można odebrać uprawnień administratora samemu sobie w ten sposób!");
        } else {
            if(isOrginalAdmin(userName)){
                errorMsg.setText("Nie można odebrać uprawnień orginalnemu administratorowi!");
            }
            else{
                if (isUsernameInDatabase(userName)) {
                    try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
                        String usernameQuery = "UPDATE users SET user_type=0 WHERE user_id=?";
                        try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                            usernameStatement.setString(1, userName);
                            usernameStatement.executeUpdate();
                            errorMsg.setText("Użytkownik " + userName + " nie jest już administratorem!");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    errorMsg.setText("Użytkownik " + userName + " nie istnieje!");
                }
            }
        }
    }

    public void selfDemotionAction(){
        CurrentUserData currentUserData = CurrentUserData.getInstance();
        String userId = String.valueOf(currentUserData.getUserId());
        String userName = userNameField.getText();
        if(isOrginalAdmin(userId)){
            errorMsg.setText("Nie można odebrać uprawnień orginalnemu administratorowi!");
        }
        else{
            try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
                String usernameQuery = "UPDATE users SET user_type=0 WHERE user_id=?";
                try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                    usernameStatement.setString(1, userId);
                    usernameStatement.executeUpdate();
                    errorMsg.setText("Nie jesteś już administratorem!");
                    Stage curentStage = (Stage) errorMsg.getScene().getWindow();
                    curentStage.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void showUsersAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("users-list.fxml"));
        Stage currentStage = (Stage) errorMsg.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }
    public void showSongsAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("songs-list.fxml"));
        Stage currentStage = (Stage) errorMsg.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }
    public void CompositorsAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("compositor-plane.fxml"));
        Stage currentStage = (Stage) errorMsg.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }
    public void AlbumsAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("albums-panel.fxml"));
        Stage currentStage = (Stage) errorMsg.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }
    public void editSongAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("song-edit-plane.fxml"));
        Stage currentStage = (Stage) errorMsg.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }

    public void createAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("song-create-plane.fxml"));
        Stage currentStage = (Stage) errorMsg.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }

    public void deleteAction() throws SQLException {
        String musicId = songId.getText();
        if (musicId == null || musicId.isEmpty()) {
            errorMsg.setText("Pole nie może być puste!");
        }
        else{
            if(isSongInDatabase(musicId)) {
                PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("DELETE FROM authors_songs WHERE songs_id=?");
                ps2.setString(1, musicId);
                ps2.executeUpdate();
                PreparedStatement ps3 = DatabaseConnection.getDatabaseConnection().prepareStatement("DELETE FROM transactions WHERE song_id=?");
                ps3.setString(1, musicId);
                ps3.executeUpdate();
                PreparedStatement ps1 = DatabaseConnection.getDatabaseConnection().prepareStatement("DELETE FROM songs WHERE song_id=?");
                ps1.setString(1, musicId);
                ps1.executeUpdate();
                errorMsg.setText("Usunięto utwór!");}
            else{
                errorMsg.setText("Taki utwór nie istnieje!");
            }
        }

    }

    private boolean isSongInDatabase(String musicId) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String usernameQuery = "SELECT * FROM songs WHERE song_id=?";
            try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                usernameStatement.setString(1, musicId);
                ResultSet rs = usernameStatement.executeQuery();
                if(rs.next()){
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
