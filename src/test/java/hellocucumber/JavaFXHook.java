package hellocucumber;

import io.cucumber.java.BeforeAll;
import javafx.application.Application;

public class JavaFXHook {
    private static Thread fxThread;

    @BeforeAll
    public static void startJavaFx() {
        if (fxThread == null) {
            fxThread = new Thread(() -> Application.launch(app.Main.class), "JavaFX Init Thread");
            fxThread.setDaemon(true);
            fxThread.start();
            // Optionally wait for the stage to show:
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
    }
}
