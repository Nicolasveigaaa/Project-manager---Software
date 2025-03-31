package app;

// Folder Imports
import ui.AuthUI;
import persistence.Database;
import app.employee.AuthValidation;

// Java utilities
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        AuthValidation auth = new AuthValidation(database);
        Scanner scanner = new Scanner(System.in);

        AuthUI.authScreen(auth, scanner);

        // Rest of the application will run if logged in successfully:
        System.out.println("logged in");
    }

}
