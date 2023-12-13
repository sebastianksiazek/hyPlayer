package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationWindowController {
    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private Label mainLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;
    @FXML
    private Label errorInfoLabel;

    @FXML
    private Button registrationButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    protected void handleRegistration() throws SQLException, IOException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        int userType = 0;
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            try {
                PreparedStatement ps1 = DatabaseConnection.getDatabaseConnection().prepareStatement("select count(*) from users where username =?");
                ps1.setString(1, usernameField.getText());
                ResultSet rs1 = ps1.executeQuery();

                PreparedStatement ps2 = DatabaseConnection.getDatabaseConnection().prepareStatement("select count(*) from users where email =?");
                ps2.setString(1, emailField.getText());
                ResultSet rs2 = ps2.executeQuery();
                int rs1count =0;
                int rs2count =0;
                if(rs1.last()){
                    rs1count = rs1.getRow();
                    rs1.beforeFirst();
                }
                if(rs2.last()){
                    rs2count = rs2.getRow();
                    rs2.beforeFirst();
                }

                if (rs1count == 0 && rs2count == 0) {
                    System.out.println(rs1.getFetchSize());
                    System.out.println(rs2.getFetchSize());
                    PreparedStatement ps3 = DatabaseConnection.getDatabaseConnection().prepareStatement("INSERT INTO users (username, email, password, user_type) VALUES (?, ?, ?, ?)");
                    ps3.setString(1, usernameField.getText());
                    ps3.setString(2, emailField.getText());
                    ps3.setString(3, passwordField.getText());
                    PreparedStatement ps4 = DatabaseConnection.getDatabaseConnection().prepareStatement("select count(*) from users");
                    ResultSet rs4 = ps4.executeQuery();
                    int rs4count =0;
                    if(rs4.last()){
                        rs4count = rs4.getRow();
                        rs4.beforeFirst();
                    }
                    if (rs4count == 0) {
                        ps3.setInt(4, 1);
                        System.out.println(rs4.getFetchSize());
                    } else {
                        ps3.setInt(4, 0);
                    }
                    int rs3 = ps3.executeUpdate();

                }


                while (rs1.next()) {
                    errorInfoLabel.setText("Błąd rejestracji login zajęty");
                }
                while (rs2.next()) {
                    errorInfoLabel.setText("Błąd rejestracji email już był użyty");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            errorInfoLabel.setText("Błąd rejestracji brak wymaganych danych");
        }

    }

    private void homePanel() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-loging-panel.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("hyPlayer");
        newStage.setScene(scene);
        newStage.show();
        Stage curentStage = (Stage) registrationButton.getScene().getWindow();
        curentStage.close();
    }

}
