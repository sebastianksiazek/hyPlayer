package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.SongModel;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UsersListController  implements Initializable {
    @FXML
    private Label usersList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usersList.setText(usersList.getText() + "\n" + "ID: "+ rs.getString("user_id") +" Nazwa " + rs.getString("username") + " email: " + rs.getString("email") + " Typ: " + rs.getString("user_type") + " Balans: "+ rs.getString("balance")+"zł");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
}
}
