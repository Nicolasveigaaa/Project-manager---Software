// [Written by s246060]
package app;

// JavaFX imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.Database;
import ui.BaseController;

// Java utilities
import java.io.IOException;

import app.project.ProjectService;

public class Main extends Application {
    private final static Database db = new Database();
    private final static ProjectService projectService = new ProjectService();

    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("prism.order", "sw"); // use the pure-java software pipeline
        System.setProperty("prism.forceGPU", "false"); // disable any GPU usage

        Parent root = FXMLLoader.load(
                getClass().getResource("/ui/FXML/authScreen.fxml"));
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();

        BaseController.init(stage);
    }

    // JAVA FX
    public static void main(String[] args) {
        launch();
    }

    // ONE SINGLE DATABASE USED BY EVERYONE
    public static Database getDatabase() {
        return db;
    }

    // ONE SINGLE PROJECT SERVICE USED BY EVERYONE
    public static ProjectService getProjectService() {
        return projectService;
    }
}