package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumsPanelController {

    @FXML
    private Label errorMsg;
    @FXML
    private TextField albumField;

    public Boolean isAlbumInDatabase(String name) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String usernameQuery = "SELECT * FROM albums WHERE album_title=?";
            try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                usernameStatement.setString(1, name);
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
    public void subbmitAction() {
        String album = albumField.getText();
        if (album == null || album.isEmpty()) {
            errorMsg.setText("Pole nie może być puste!");
        }
        else{
            if(isAlbumInDatabase(album)){
                errorMsg.setText("Taki album już istnieje!");
            }
            else{
                try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
                    String usernameQuery = "INSERT INTO albums (album_title) VALUES (?)";
                    try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                        usernameStatement.setString(1, album);
                        usernameStatement.executeUpdate();
                        errorMsg.setText("Dodano album!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
