module com.overcooked.ptut {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.overcooked.ptut to javafx.fxml;
    exports com.overcooked.ptut;
}