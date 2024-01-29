package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SongCreatePlaneController {
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
        String name = nameField.getText();
        String author = authorField.getText();
        String album = albumField.getText();
        String genre = genreField.getText();
        String price = priceField.getText();
        authorIdSearch();
        albumIdSearch();
        if (albumId == null || authorId == null ||name == null || name.isEmpty() || author == null || author.isEmpty() || album == null || album.isEmpty() || genre == null || genre.isEmpty() || price == null || price.isEmpty()) {
            errorMsg.setText("Pola nie mogą być puste!");
        }
        else{
            try {
                int songId;
                float songPrice = Float.parseFloat(price);
                PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("INSERT INTO songs (song_title,genre,price,album_id,image_url) VALUES (?,?,?,?,?);");
                ps.setString(1, name);
                ps.setString(2, genre);
                ps.setFloat(3, songPrice);
                ps.setInt(4, Integer.parseInt(albumId));
                String urs = imagePathUrl;
                ps.setString(5, urs);
                ps.executeUpdate();

                PreparedStatement ps3 = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT song_id FROM songs WHERE song_title = ? AND price =?;");
                ps3.setString(1, name);
                ps3.setFloat(2, songPrice);
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    songId = rs3.getInt("song_id");
                    PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("INSERT INTO authors_songs (authors_id, songs_id) VALUES (?, ?);");
                    ps2.setInt(1, Integer.parseInt(authorId));
                    ps2.setInt(2, songId);
                    ps2.executeUpdate();
                }








                errorMsg.setText("Pomyślnie zaktualizowano piosenkę!");
            } catch (NumberFormatException e) {
                errorMsg.setText("Niepoprawny format ID lub ceny!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
