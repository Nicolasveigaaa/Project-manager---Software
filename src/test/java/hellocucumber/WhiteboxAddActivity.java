package hellocucumber;

import io.cucumber.java.en.*;
import app.project.ProjectService;
import domain.Project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;


public class WhiteboxAddActivity {
    private String projectID;
    private String activityName;
    private double budgetedTime;
    private String ID;
    private int startWeek = 43;
    private int endWeek = 46;
    private int startYear = 2025;
    private int endYear = 2026;
    private Project activeProject;
    private Exception caughtException;

    ProjectService projectService = new ProjectService();




    @Given("a projectID {string}")
    public void a_projectID(String s) {
        projectID = s;
    }

    @Given("a activity name {string}")
    public void a_activity_name(String s) {
        activityName = s;
    }

    @Given("a budgeted time of {int}")
    public void a_budgetedTime_of(int i) {
        budgetedTime = i;
    }

    @Given("the project exist")
    public void the_project_exist() {
        ID = ProjectService.addProject(projectID);
    }

    @Given("the project does not exist")
    public void the_project_does_not_exist() {
    }

    @Given("the activity name already exist in project")
    public void the_activity_name_already_exist_in_project() {
        activeProject = projectService.createActivityForProject(ID, activityName, budgetedTime, startWeek, endWeek, startYear, endYear);
    }

    @Given("the activity name is unique")
    public void the_activity_name_is_unique() {
        Project project = projectService.findProjectByID(ID).get();
        assertFalse(project.getActivityByName(activityName) != null);
    }

    @When("the activity is created")
    public void the_activity_is_created() {
        try{
            activeProject = projectService.createActivityForProject(ID, activityName, budgetedTime, startWeek, endWeek, startYear, endYear);
        }catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("the activity is created Successfully")
    public void the_activity_is_created_Successfully() {
        assertTrue(activeProject.getActivityByName(activityName) != null);
    }



}
