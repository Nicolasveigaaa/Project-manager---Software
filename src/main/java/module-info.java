module hellofx {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens app to javafx.graphics;
    opens ui.FXML to javafx.fxml;
    opens ui.Controllers to javafx.fxml;
}