module com.example.orchesterfx {
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.orchesterfx to javafx.fxml;
    opens com.example.orchesterfx.model to javafx.base;

    exports com.example.orchesterfx;
    exports com.example.orchesterfx.model;
}