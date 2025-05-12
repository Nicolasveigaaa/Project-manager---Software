module hellofx {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens app to javafx.graphics;
    opens ui.Controllers to javafx.fxml;
    opens ui to javafx.fxml;
}