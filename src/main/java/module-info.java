module com.cculp.cculpprimenumberclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cculp.cculpprimenumberclient to javafx.fxml;
    exports com.cculp.cculpprimenumberclient;
}