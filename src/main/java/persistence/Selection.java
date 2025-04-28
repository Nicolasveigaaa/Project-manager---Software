package persistence;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.Employee;
import domain.Project;
import app.Main;
import javafx.scene.chart.PieChart.Data;
import ui.SelectionUI;
import ui.ProjectUI.*;

public class Selection {
    private final Map<String, List<String>> options;
    private final Database dataBase;

    public Selection(Database dataBase) {
        System.out.println("Selection");
        // Set the default options
        options = Map.of(
                "home", List.of("Projects", "Total Hours"),
                "Projects", List.of("Create Project", "View Active Projects"),
                "3", List.of("T3", "T6", "t3", "t6")
        );

        this.dataBase = dataBase;
    }

    public List<String> getOptions(String option) {
        // Get the specific option
        return options.get(option);
    }

    public String getSelectedOption(String option, String optionNumber) {
        // Get the specific option
        return options.get(option).get(Integer.parseInt(optionNumber)-1);
    }

    // Save the selected options
    private Employee user_db = null;
    private String option = "";

    public boolean isValidOption(String option, String optionNumber) {
        List<String> groupOptions = options.get(option);

        // Check if the option is valid
        boolean isValid = false;

        if (groupOptions.get(Integer.parseInt(optionNumber)-1) != "") {isValid = true;};

        // Get user from database
        this.user_db = dataBase.getUser(Main.getInitials());
        this.option = getSelectedOption(option, optionNumber);

        return groupOptions != null && isValid;
    }

    public void activateCategory(Selection selection, Scanner scanner) {
        if (option.equals("Total Hours")) {
            new ui.UnderCategoryUI.totalHours().showTotalHours(user_db);
        }
        if (option.equals("Projects")) {
            SelectionUI.choseCategory(selection, scanner, "Projects");
        }
        if (option.equals("Create Project")) {
            // Create new project
            String projectName = createProjectUI.createNewProject(scanner);
            Project newProject = new Project(projectName);
            
        }
    }
}
