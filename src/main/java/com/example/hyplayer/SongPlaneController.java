package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.SongModel;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SongPlaneController implements Initializable {
    @FXML
    private ImageView songImage;
    @FXML
    private Label songName;
    @FXML
    private Label songAuthor;
    @FXML
    private Label songAlbum;
    @FXML
    private Label songPrice;
    @FXML
    private Label songGenre;
    @FXML
    private Label userBalance;
    @FXML
    private Label errorMsg;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CurrentSong currentSong = CurrentSong.getInstance();
        String songId = String.valueOf(currentSong.getSongId());
        try {
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM songs where song_id=?");
            ps.setString(1, songId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Image image = new Image(getClass().getResourceAsStream(rs.getString("image_url")));
                songImage.setImage(image);
                songName.setText(rs.getString("song_title"));
                try{
                    PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT al.album_title AS album_name FROM songs s LEFT JOIN albums al ON s.album_id = al.album_id WHERE s.song_id = ?;");
                    ps2.setString(1, songId);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        songAlbum.setText(rs2.getString("album_name"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                songPrice.setText(String.valueOf(rs.getFloat("price")));
                songGenre.setText(rs.getString("genre"));



            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM users where user_id=?");
            ps.setString(1, String.valueOf(CurrentUserData.getInstance().getUserId()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userBalance.setText(String.valueOf(rs.getFloat("balance")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT a.name AS author_name FROM songs s JOIN authors_songs ASr ON s.song_id = ASr.songs_id JOIN authors a ON ASr.authors_id = a.author_id WHERE s.song_id = ?;");
            ps.setString(1, songId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                songAuthor.setText(rs.getString("author_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void didUserOwnSong(String userId, String songId) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM transactions WHERE user_id = ? AND song_id = ?");
        ps.setString(1, userId);
        ps.setString(2, songId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            errorMsg.setText("Już posiadasz tą piosenkę!");
        }
        else{
            buyHandle();
        }
    }
    public void buyHandle(){
        CurrentUserData currentUserData = CurrentUserData.getInstance();
        String userId = String.valueOf(currentUserData.getUserId());
        CurrentSong currentSong = CurrentSong.getInstance();
        String songId = String.valueOf(currentSong.getSongId());
        try{
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM users where user_id=?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(rs.getFloat("balance") >= Float.parseFloat(songPrice.getText())){
                    try{
                        PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("INSERT INTO transactions (user_id, song_id,price,data) VALUES (?, ?,?,NOW())");
                        ps2.setString(1, userId);
                        ps2.setString(2, songId);
                        ps2.setString(3, songPrice.getText());
                        ps2.executeUpdate();

                        PreparedStatement ps3 = DatabaseConnection.getDatabaseConnection().prepareStatement("UPDATE users SET balance = ? WHERE user_id = ?");
                        ps3.setString(1, String.valueOf(rs.getFloat("balance") - Float.parseFloat(songPrice.getText())));
                        ps3.setString(2, userId);
                        ps3.executeUpdate();
                        userBalance.setText(String.valueOf(rs.getFloat("balance") - Float.parseFloat(songPrice.getText())));
                        errorMsg.setText("Zakupiono piosenkę!");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    errorMsg.setText("Nie masz wystarczająco środków!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void buyAction() throws SQLException {
        didUserOwnSong(String.valueOf(CurrentUserData.getInstance().getUserId()), String.valueOf(CurrentSong.getInstance().getSongId()));
    }
}
