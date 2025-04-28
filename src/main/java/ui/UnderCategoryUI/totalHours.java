package ui.UnderCategoryUI;

import domain.Employee;
// Folder imports
import persistence.Database;

public class totalHours {
    public void showTotalHours(Employee user_db) {
        Double totalHours = user_db.getTotalHours();
        // Print total hours
        System.out.println("Total hours: "+ totalHours);
    }
}
