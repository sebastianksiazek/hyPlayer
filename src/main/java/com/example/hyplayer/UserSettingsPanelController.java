package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SongModel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserSettingsPanelController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private Button submittButton;
    @FXML
    public boolean isDatabaseEmpty(String userId) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String usernameQuery = "SELECT * FROM personal_data WHERE user_id=?";
            try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                usernameStatement.setString(1, userId);
                ResultSet usernameResult = usernameStatement.executeQuery();
                if (usernameResult.next()) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    private void submittButtonAction() throws SQLException, IOException {
        CurrentUserData currentUserData = CurrentUserData.getInstance();
        String userId = String.valueOf(currentUserData.getUserId());
        if(!isDatabaseEmpty(userId)){
                PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("INSERT INTO personal_data (user_id, name, surname, address, city, postcode) VALUES (?, ?, ?, ?, ?, ?)");
                ps2.setString(1, userId);
                ps2.setString(2, nameField.getText());
                ps2.setString(3, surnameField.getText());
                ps2.setString(4, addressField.getText());
                ps2.setString(5, cityField.getText());
                ps2.setString(6, zipCodeField.getText());
                int rowsAffected = ps2.executeUpdate();
                if (rowsAffected > 0) {
                    comeBack();
                }
            }
        else{
                try {
                    PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("UPDATE personal_data SET name = ?, surname = ?, address = ?, city = ?, postcode = ? WHERE user_id = ?");
                    ps2.setString(1, nameField.getText());
                    ps2.setString(2, surnameField.getText());
                    ps2.setString(3, addressField.getText());
                    ps2.setString(4, cityField.getText());
                    ps2.setString(5, zipCodeField.getText());
                    ps2.setString(6, userId);
                    int rowsAffected = ps2.executeUpdate();
                    if (rowsAffected > 0) {
                        comeBack();
                    }
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
        }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CurrentUserData currentUserData = CurrentUserData.getInstance();
        String userId = String.valueOf(currentUserData.getUserId());
        try {
            PreparedStatement ps = DatabaseConnection.getDatabaseConnection().prepareStatement("SELECT * FROM personal_data WHERE user_id = ?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nameField.setText(rs.getString("name"));
                surnameField.setText(rs.getString("surname"));
                addressField.setText(rs.getString("address"));
                cityField.setText(rs.getString("city"));
                zipCodeField.setText(rs.getString("postcode"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void comeBack() throws IOException {

        Stage curentStage = (Stage) submittButton.getScene().getWindow();
        curentStage.close();
    }
}
