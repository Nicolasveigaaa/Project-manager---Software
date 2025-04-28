package ui.ProjectUI;

import java.util.Scanner;

public class createProjectUI {
    public static String createNewProject(Scanner scanner) {
        System.out.println();
        System.out.println("// Creating project //");
        System.out.println("Project name: ");

        String name = scanner.nextLine().trim().toLowerCase();

        if (name.length() > 0) {
            return name;
        } else {
            return "";
        }
    }

    public static void updateUser(String projectName, String projectID, boolean isCreated) {
        System.out.println();

        if (!isCreated) {
            System.out.println(">> Project creation failed <<");
            System.out.println("Project name: " + projectName);
            System.out.println();
            return;
        }
        System.out.println(" >> Project created successfully <<");
        System.out.println("Project name: " + projectName);
        System.out.println("Project ID: " + projectID);
        System.out.println("Project manager: Not assigned");

        System.out.println();
    }
}