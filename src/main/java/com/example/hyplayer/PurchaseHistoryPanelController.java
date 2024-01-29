package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PurchaseHistoryPanelController implements Initializable {
    @FXML
    private Label historyLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM transactions WHERE user_id = ?;");
            CurrentUserData currentUserData = CurrentUserData.getInstance();
            ps.setInt(1, currentUserData.getUserId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM songs WHERE song_id = ?;");
                ps2.setInt(1, rs.getInt("song_id"));
                ResultSet rs2 = ps2.executeQuery();
                rs2.next();
                historyLabel.setText(historyLabel.getText() + "\n" + "ID: "+ rs.getString("transaction_id") +" Data: " + rs.getString("data") + " Kwota: " + rs.getString("price") + "zł" + " Tytuł: " + rs2.getString("song_title"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
