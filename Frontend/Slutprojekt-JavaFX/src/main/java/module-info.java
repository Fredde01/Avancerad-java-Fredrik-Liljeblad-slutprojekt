module com.example.slutprojektjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.databind;

    opens com.example.slutprojektjavafx to javafx.fxml;
    exports com.example.slutprojektjavafx;
}