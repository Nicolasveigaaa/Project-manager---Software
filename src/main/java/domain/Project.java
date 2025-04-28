package domain;

import java.util.Scanner;

public class Project {
    private String projectID;
    private String projectName;
    private String projectManager;

    private final Object availableChars[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z"};
    private final String availableNumbs = "1234567890";

    private String createID() {
        // Split the available nums into seperate numbers
        Character numbs[] = {};
        for (int i = 0; i < availableNumbs.length(); i++) {
            numbs[i] = availableNumbs.charAt(i);
        }
        // Make it 6 ciffre

        return null;
    }

    public Project(String projectName) {
        this.projectID = createID();
        this.projectName = projectName;
        this.projectManager = "Not assigned";
    }
}
