package com.example.hyplayer;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SongEditPlaneController  {
    @FXML
    private Label errorMsg;
    @FXML
    private TextField nameField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField albumField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField idField;
    @FXML
    private ImageView imageUrl;
    private String albumId;
    private String authorId;
    private String imagePathUrl;

    public void loadAction(){
        String id = idField.getText();
        if (id == null || id.isEmpty()) {
            errorMsg.setText("Pole nie może być puste!");
        }
        else{
            try {
                int songId = Integer.parseInt(id);
                PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM songs where song_id=?");
                ps.setString(1, String.valueOf(songId));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    nameField.setText(rs.getString("song_title"));
                    genreField.setText(rs.getString("genre"));
                    priceField.setText(String.valueOf(rs.getFloat("price")));
                    Image image = new Image(getClass().getResourceAsStream(rs.getString("image_url")));
                    imageUrl.setImage(image);

                }
                PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT al.album_title AS album_name FROM songs s LEFT JOIN albums al ON s.album_id = al.album_id WHERE s.song_id = ?;");
                ps2.setString(1, String.valueOf(songId));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    albumField.setText(rs2.getString("album_name"));
                }
                PreparedStatement ps3 = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT a.name AS author_name FROM songs s JOIN authors_songs ASr ON s.song_id = ASr.songs_id JOIN authors a ON ASr.authors_id = a.author_id WHERE s.song_id = ?;");
                ps3.setString(1, String.valueOf(songId));
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    authorField.setText(rs3.getString("author_name"));
                }

            } catch (NumberFormatException e) {
                errorMsg.setText("Niepoprawny format ID!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void albumIdSearch(){
        String album = albumField.getText();
        if (album == null || album.isEmpty()) {
            errorMsg.setText("Pole nie może być puste!");
        }
        else{
            try {
                PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT album_id FROM albums WHERE album_title = ?;");
                ps.setString(1, album);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    albumId =String.valueOf(rs.getInt("album_id"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void authorIdSearch(){
        String author = authorField.getText();
        if (author == null || author.isEmpty()) {
            errorMsg.setText("Pole nie może być puste!");
        }
        else{
            try {
                PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT author_id FROM authors WHERE name = ?;");
                ps.setString(1, author);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    authorId =String.valueOf(rs.getInt("author_id"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void imageAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz obrazek");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Obrazy", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*")
        );

        // Pokaż okno dialogowe wyboru pliku
        File selectedFile = fileChooser.showOpenDialog(null);

        // Jeśli użytkownik wybrał plik, wczytaj go i ustaw jako obrazek
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                String imagePath = selectedFile.toURI().toString();
                // Zmiana ścieżki do pliku na zaczynającą się od "/songsImages"
                imagePath = "/songsImages" + imagePath.substring(imagePath.lastIndexOf('/'));
                System.out.println(imagePath);
                 image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                imageUrl.setImage(image);
                imagePathUrl = imagePath;

            } catch (Exception e) {
                // Obsługa błędów, jeśli wystąpią problemy z ładowaniem obrazka
                e.printStackTrace();
                errorMsg.setText("Wystąpił błąd podczas wczytywania obrazka!");
            }
        }
    }

    public void submitAction(){
        String id = idField.getText();
        String name = nameField.getText();
        String author = authorField.getText();
        String album = albumField.getText();
        String genre = genreField.getText();
        String price = priceField.getText();
        authorIdSearch();
        albumIdSearch();
        if (id == null || albumId == null || authorId == null || id.isEmpty() || name == null || name.isEmpty() || author == null || author.isEmpty() || album == null || album.isEmpty() || genre == null || genre.isEmpty() || price == null || price.isEmpty()) {
            errorMsg.setText("Pola nie mogą być puste!");
        }
        else{
            try {
                int songId = Integer.parseInt(id);
                float songPrice = Float.parseFloat(price);
                PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("UPDATE songs SET song_title = ?, genre = ?, price = ? ,album_id = ? WHERE song_id = ?;");
                ps.setString(1, name);
                ps.setString(2, genre);
                ps.setFloat(3, songPrice);
                ps.setInt(4, Integer.parseInt(albumId));
                ps.setInt(5, songId);
                ps.executeUpdate();

                PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("INSERT INTO authors_songs (authors_id, songs_id) VALUES (?, ?);");
                ps2.setInt(1, Integer.parseInt(authorId));
                ps2.setInt(2, songId);
                ps2.executeUpdate();

                PreparedStatement ps3 = DatabaseConnection.getDatabaseConnection().prepareStatement("UPDATE songs SET image_url = ? WHERE song_id = ?;");
                String urs = imagePathUrl;
                ps3.setString(1, urs);
                ps3.setInt(2, songId);
                ps3.executeUpdate();






                errorMsg.setText("Pomyślnie zaktualizowano piosenkę!");
            } catch (NumberFormatException e) {
                errorMsg.setText("Niepoprawny format ID lub ceny!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
