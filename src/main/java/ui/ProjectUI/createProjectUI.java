package ui.ProjectUI;

import java.util.Scanner;

public class createProjectUI {
    public static String createNewProject(Scanner scanner) {
        System.out.println();
        System.out.println("// Creating project //");
        System.out.println("Project name: ");

        String name = scanner.nextLine().trim().toLowerCase();

        if (name != "") {
            return name;
        } else {
            return "";
        }
    }
}