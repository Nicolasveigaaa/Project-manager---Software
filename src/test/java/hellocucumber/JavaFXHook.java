 // [Written by s244706] //

package hellocucumber;

import app.Main;
import app.employee.AuthValidation;
import domain.User;
import io.cucumber.java.BeforeAll;
import javafx.application.Application;
import persistence.Database;

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

        Database db = Main.getDatabase();
        // Initialize with a default user
        User defaultUser = new User("AB");
        db.addUser(defaultUser);
        
        AuthValidation auth = new AuthValidation(db);
        auth.validateLogin("AB");
    }
}
