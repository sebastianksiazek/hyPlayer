package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SongsListController  implements Initializable {
    @FXML
    private Label songsList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM songs");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                songsList.setText(songsList.getText() + "\n" + "ID: "+ rs.getString("song_id") +" Nazwa " + rs.getString("song_title") + " Album: " + rs.getString("album_id") + " Cena: " + rs.getString("price") + " Gatunek: "+ rs.getString("genre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
