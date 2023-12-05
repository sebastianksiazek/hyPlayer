module com.example.hypelay {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;

    opens com.example.hypelay to javafx.fxml;
    exports com.example.hypelay;
}