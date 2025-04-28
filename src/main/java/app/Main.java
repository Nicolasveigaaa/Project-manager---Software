package app;

// Folder Imports
import ui.AuthUI;
import ui.SelectionUI;

import persistence.Database;
import persistence.Selection;
import app.employee.AuthValidation;

// Java utilities
import java.util.Scanner;

public class Main {
    private static String initials = "";

    public static void main(String[] args) {
        Database database = new Database();
        Selection selection = new Selection(database);
        AuthValidation auth = new AuthValidation(database);
        Scanner scanner = new Scanner(System.in);

        initials = AuthUI.authScreen(auth, scanner);

        // Rest of the application will run if logged in successfully:
        System.out.println("logged in");

        // Show menu screen
        SelectionUI.choseCategory(selection, scanner, "home");
        selection.activateCategory(selection, scanner);
    }

    public static String getInitials() {
        return initials;
    }
}
