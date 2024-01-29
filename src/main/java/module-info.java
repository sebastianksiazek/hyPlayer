module com.example.hyplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires  java.sql;
    requires  mysql.connector.j;

    opens com.example.hyplayer to javafx.fxml;
    exports com.example.hyplayer;
    exports model;
    opens model to javafx.fxml;
}