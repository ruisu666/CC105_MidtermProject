module com.mycompany.gumana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires java.desktop;

    opens com.mycompany.gumana to javafx.fxml;
    exports com.mycompany.gumana;
}
