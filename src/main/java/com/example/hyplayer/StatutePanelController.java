package com.example.hyplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class StatutePanelController implements Initializable {
    @FXML
    private Label statuteId;

    //private String readFile(String filePath, Charset charset) {
       // StringBuilder content = new StringBuilder();
      //  try (BufferedReader br = new BufferedReader(new FileReader(filePath, charset))) {
       //     String line;
      //      while ((line = br.readLine()) != null) {
      //         content.append(line).append("\n");
      //      }
     //   } catch (IOException e) {
      //      e.printStackTrace();
            // Tutaj możesz obsłużyć wyjątek bardziej szczegółowo
//}
//return content.toString();
   // }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // String content = readFile("/regul/regulamin.txt", StandardCharsets.UTF_8);
      //  statuteId.setText(content);
    }
}
