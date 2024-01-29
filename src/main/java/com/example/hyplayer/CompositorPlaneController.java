package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompositorPlaneController {
    @FXML
    private Label errorMsg;
    @FXML
    private TextField compositorField;


    public Boolean isCompositorInDatabase(String name) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String usernameQuery = "SELECT * FROM authors WHERE name=?";
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
        String compositor = compositorField.getText();
        if (compositor == null || compositor.isEmpty()) {
            errorMsg.setText("Pole nie może być puste!");
        }
        else{
            if(isCompositorInDatabase(compositor)){
                errorMsg.setText("Taki kompozytor już istnieje!");
            }
            else{
                try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
                    String usernameQuery = "INSERT INTO authors (name) VALUES (?)";
                    try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                        usernameStatement.setString(1, compositor);
                        usernameStatement.executeUpdate();
                        errorMsg.setText("Dodano kompozytora!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
