package app.report;

import domain.Project;
import domain.Activity;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map; // Import Map

public class CreateReport {

    // Calculates the total budgeted time and total logged time for a given project.

    public static Map<String, Double> generateProjectTimeSummary(Project project) {
        Map<String, Double> summary = new HashMap<>();
        // if (project == null) {
        //     System.err.println("Cannot generate report for a null project.");
        //     // Return empty map or throw exception based on desired handling
        //      summary.put("totalBudgeted", 0.0);
        //      summary.put("totalLogged", 0.0);
        //     return summary;
        // }

        double totalBudgeted = 0.0;
        double totalLogged = 0.0;

        Collection<Activity> activities = project.getActivities();
        if (activities != null) {
            for (Activity activity : activities) {
                totalBudgeted += activity.getBudgetedTime();
                totalLogged += activity.getLoggedTime();
            }
        }

        summary.put("totalBudgeted", totalBudgeted);
        summary.put("totalLogged", totalLogged);

        return summary;
    }
}