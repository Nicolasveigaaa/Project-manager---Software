package ui;

import app.employee.AuthValidation;
import persistence.Database;

import java.util.Scanner;

public class AuthUI {
    public static String authScreen(AuthValidation auth, Scanner scanner) {
        System.out.println("=== Welcome to Time Registration System ===");

        while (true) {
            System.out.print("Enter your initials: ");
            String initials = scanner.nextLine().trim().toLowerCase();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine().trim();

            if (auth.validateLogin(initials, password)) {
                System.out.println("✅ Login successful! Welcome, " + initials);
                return initials; // Return the initials of the logged-in user
            } else {
                System.out.println("❌ Invalid credentials. Try again.\n");
            }
        }
    }
}