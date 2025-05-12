// [Written by s244706 and s246060] //
package ui;

// Java
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

// JavaFX
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseController {
    private static Stage primaryStage;
    private static final Deque<String> history = new ArrayDeque<>();

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    /** Expose the stage to test code */
    public static Stage getStage() {
        return primaryStage;
    }

    public static void goTo(String fxmlPath) throws IOException {
        if (primaryStage == null) {
            throw new IllegalStateException("Navigator not initialized");
        }

        // Get (or create) the current Scene
        Scene scene = primaryStage.getScene();
        if (scene != null) {
            // Push its current root
            history.push(fxmlPath);
        }

        // Load next view
        Parent next = FXMLLoader.load(
            BaseController.class.getResource(fxmlPath)
        );

        if (scene == null) {
            // First navigation: create a Scene
            Scene newScene = new Scene(next);
            primaryStage.setScene(newScene);
        } else {
            // Reuse existing Scene by swapping its root
            scene.setRoot(next);
        }
    }

    public static void goBack() {
        for (String s : history) {
            System.out.println(s);
        }

        if (primaryStage == null) {
            throw new IllegalStateException("Navigator not initialized");
        }
        if (history.isEmpty()) {
            return; // nothing to go back to
        }

        String previousFxml;

        if (history.peek().equals("/ui/FXML/homeScreen.fxml")) {
            previousFxml = "/ui/FXML/homeScreen.fxml";
        } else {
            previousFxml = history.pop();
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                BaseController.class.getResource(previousFxml)
            );
            Parent previous = loader.load();
            
            Scene scene = primaryStage.getScene();
            if (scene == null) {
                primaryStage.setScene(new Scene(previous));
            } else {
                scene.setRoot(previous);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pushHistory(String fxmlPath) {
        history.push(fxmlPath);
    }

    @FXML
    public void handleBack(ActionEvent e) {
        BaseController.goBack();
    }
}