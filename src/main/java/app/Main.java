package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(
            getClass().getResource("/ui/FXML/authScreen.fxml")
        );
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String getInitials() {
        return "hey";
    }

    public static void setInitials(String initials) {
        // Set the initials for the application
        System.out.println("Initials set to: " + initials);
    }
}