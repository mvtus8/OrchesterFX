module com.example.orchesterfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.orchesterfx to javafx.fxml;
    exports com.example.orchesterfx;
}