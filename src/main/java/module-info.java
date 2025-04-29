module hellofx {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens app to javafx.graphics;
    opens ui.Controllers to javafx.fxml;
}