package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SongModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePanelController implements Initializable {
    @FXML
    private HBox allSongsHBox;
    @FXML
    private HBox usersSongsBox;
    @FXML
    private Button homeOptionButton;
    @FXML
    private ImageView changeMode;


    List<SongModel> allSongs;

    @FXML
    private void homeOptionButtonAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("settings-panel.fxml"));
        Stage currentStage = (Stage) homeOptionButton.getScene().getWindow(); //Current Stage
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        currentStage.toFront();
    }

    public void initialize(URL location, ResourceBundle resources){
        allSongs = new ArrayList<>(getAllSongs());
        try{
            for (SongModel songModel : allSongs) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song-box.fxml"));

                VBox vBox = fxmlLoader.load();
                SongModelController songModelController = fxmlLoader.getController();
                songModelController.setData(songModel);

                allSongsHBox.getChildren().add(vBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        allSongs = new ArrayList<>(getAllSongs2());
        try{
            for (SongModel songModel : allSongs) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song-box.fxml"));

                VBox vBox = fxmlLoader.load();
                SongModelController songModelController = fxmlLoader.getController();
                songModelController.setData(songModel);

                usersSongsBox.getChildren().add(vBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<SongModel>getAllSongs() {
        List<SongModel> allSongs = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM songs");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SongModel songModel = new SongModel();
                songModel.setImagePath(rs.getString("image_url"));
                songModel.setSongName(rs.getString("song_title"));
                songModel.setSongId(rs.getInt("song_id"));
                allSongs.add(songModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allSongs;
    }
    private List<SongModel>getAllSongs2() {
        List<SongModel> allSongs = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT songs.* FROM songs INNER JOIN transactions ON songs.song_id = transactions.song_id WHERE transactions.user_id = ?;\n");
            ps.setString(1, String.valueOf(CurrentUserData.getInstance().getUserId()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SongModel songModel = new SongModel();
                songModel.setImagePath(rs.getString("image_url"));
                songModel.setSongName(rs.getString("song_title"));
                songModel.setSongId(rs.getInt("song_id"));
                allSongs.add(songModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allSongs;
    }



}
