module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.compiler;
    requires java.sql;
    requires static lombok;


    opens com.example.library to javafx.fxml;
    opens com.example.library.controllers to javafx.fxml;
    opens com.example.library.models to javafx.fxml;

    exports com.example.library;
    exports com.example.library.controllers;
    exports com.example.library.models;
}