module com.hackhud.xml_reader_lab2_oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    requires org.kordamp.bootstrapfx.core;

    opens com.hackhud.xml_reader_lab2_oop to javafx.fxml;
    exports com.hackhud.xml_reader_lab2_oop;
}