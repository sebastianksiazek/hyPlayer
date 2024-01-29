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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public static boolean isDatabaseEmpty() {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String query = "SELECT COUNT(*) FROM users";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int rowCount = resultSet.getInt(1);
                    return rowCount == 0; // Jeśli liczba rekordów wynosi zero, baza danych jest pusta
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isUsernameExists(String username) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String usernameQuery = "SELECT * FROM users WHERE username=?";
            try (PreparedStatement usernameStatement = connection.prepareStatement(usernameQuery)) {
                usernameStatement.setString(1, username);
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

    public boolean isEmailExists(String email) {
        try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
            String emailQuery = "SELECT * FROM users WHERE email=?";
            try (PreparedStatement emailStatement = connection.prepareStatement(emailQuery)) {
                emailStatement.setString(1, email);
                ResultSet emailResult = emailStatement.executeQuery();
                if (emailResult.next()) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String hashPasswordSHA1(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Konwersja bajtów na heksadecymalny zapis
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    protected void handleRegistration() throws SQLException, IOException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        int userType;
        int orginalAdmin;

        if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if(isDatabaseEmpty()) {
                userType = 1;
                orginalAdmin =1;
                try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
                    String hashedPassword = hashPasswordSHA1(password);
                    String query = "INSERT INTO users (username, email, password, user_type,orginal_admin,balance) VALUES (?, ?, ?, ?, ?,?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, email);
                        preparedStatement.setString(3, hashedPassword);
                        preparedStatement.setInt(4, userType);
                        preparedStatement.setInt(5, orginalAdmin);
                        preparedStatement.setFloat(6, 1000);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            homePanel();
                        } else {
                            errorInfoLabel.setText("Błąd rejestracji");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                if (!isUsernameExists(username) && !isEmailExists(email)) {
                    userType = 0;
                    orginalAdmin =0;
                    try (Connection connection = DatabaseConnection.getDatabaseConnection()) {
                        String hashedPassword = hashPasswordSHA1(password);
                        String query = "INSERT INTO users (username, email, password, user_type,orginal_admin,balance) VALUES (?, ?, ?, ?,?,?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, username);
                            preparedStatement.setString(2, email);
                            preparedStatement.setString(3, hashedPassword);
                            preparedStatement.setInt(4, userType);
                            preparedStatement.setInt(5, orginalAdmin);
                            preparedStatement.setFloat(6, 1000);

                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                homePanel();
                            } else {
                                errorInfoLabel.setText("Błąd rejestracji");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if (isUsernameExists(username) && isEmailExists(email)){
                    errorInfoLabel.setText("Użytkownik o podanym nicku i emailu istnieje");
                } else if (isUsernameExists(username)){
                    errorInfoLabel.setText("Użytkownik o podanym nicku istnieje");
                } else if(isEmailExists(email)) {
                    errorInfoLabel.setText("Użytkownik o podanym emailu istnieje");
                }
            }
        }
        else{
            errorInfoLabel.setText("Błąd rejestracji brak wymaganych danych");
        }

    }

    private void homePanel() throws IOException {

        Stage curentStage = (Stage) registrationButton.getScene().getWindow();
        curentStage.close();
    }

}
