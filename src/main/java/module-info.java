module com.example.synthesizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit.platform.console.standalone;
    requires java.desktop;


    opens com.example.synthesizer to javafx.fxml;
    exports com.example.synthesizer;
}