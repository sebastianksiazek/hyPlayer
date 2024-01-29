package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SongModel;

import java.io.IOException;
import java.util.Objects;

public class SongModelController {
    @FXML
    private ImageView songBoxImage;
    @FXML
    private Label songBoxName;
    @FXML
    private Label songBoxAuthor;
    private int songId;

    public void setData(SongModel songModel) {
        Image image = new Image(getClass().getResourceAsStream(songModel.getImagePath()));
        songId = songModel.getSongId();
        songBoxImage.setImage(image);
        songBoxName.setText(songModel.getSongName());
        songBoxAuthor.setText(songModel.getSongAuthor());
    }
    public void boxOnClick() throws IOException {
        CurrentSong currentSong = CurrentSong.getInstance();
        currentSong.setSongId(songId);
        Parent root = FXMLLoader.load(getClass().getResource("song-plane.fxml"));
        Stage currentStage = (Stage) songBoxImage.getScene().getWindow(); //Current Stage
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
