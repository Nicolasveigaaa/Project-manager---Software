package ui;// Java utilities
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        authScreen(scanner);
    }

    public static void authScreen(Scanner scanner) {
        System.out.println("Enter your initials:");
        String initials = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        System.out.println("You entered: " + initials + " " + password);
    }
}
