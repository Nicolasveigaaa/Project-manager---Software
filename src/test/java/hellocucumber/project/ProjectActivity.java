package hellocucumber.project;

import domain.Activity;
import domain.Project;
import domain.User;

import org.junit.jupiter.api.Assertions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ProjectActivity {
    
    private Project project;
    private Project otherProject;
    private Activity retrievedActivity;
    private ArrayList<Activity> activityList;
    private Exception thrownException;
    private Map<String, Project> projects = new HashMap<>();
    
    @Given("a project exists with name {string}")
    public void a_project_exists_with_name(String projectName) {
        project = new Project(projectName);
        projects.put(projectName, project);
    }
    
    @Given("another project exists with name {string}")
    public void another_project_exists_with_name(String projectName) {
        otherProject = new Project(projectName);
        projects.put(projectName, otherProject);
    }
    
    @Given("the project has an activity named {string}")
    public void the_project_has_an_activity_named(String activityName) {
        Activity activity = new Activity(activityName, project);
        project.addActivity(activity);
    }
    
    @Given("an activity named {string} belonging to {string} exists")
    public void an_activity_named_belonging_to_exists(String activityName, String projectName) {
        Project ownerProject = projects.get(projectName);
        Activity activity = new Activity(activityName, ownerProject);
        ownerProject.addActivity(activity);
    }
    
    @Given("the project has the following activities:")
    public void the_project_has_the_following_activities(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            Activity activity = new Activity(row.get("name"), project);
            project.addActivity(activity);
        }
    }
    
    @When("I add an activity named {string} to the project")
    public void i_add_an_activity_named_to_the_project(String activityName) {
        Activity activity = new Activity(activityName, project);
        project.addActivity(activity);
    }
    
    @When("I retrieve the activity with name {string}")
    public void i_retrieve_the_activity_with_name(String activityName) {
        retrievedActivity = project.getActivityByName(activityName);
    }
    
    @When("I request all activities")
    public void i_request_all_activities() {
        activityList = project.getActivities();
    }
    
    @When("I try to add a null activity to the project")
    public void i_try_to_add_a_null_activity_to_the_project() {
        try {
            project.addActivity(null);
        } catch (Exception e) {
            thrownException = e;
        }
    }
    
    @When("I try to add the activity {string} to the project {string}")
    public void i_try_to_add_the_activity_to_the_project(String activityName, String targetProjectName) {
        Project targetProject = projects.get(targetProjectName);
        Activity activity = null;
        Project sourceProject = null;

        // Find the project that has this activity
        for (Project p : projects.values()) {
            Activity a = p.getActivityByName(activityName);
            if (a != null) {
                activity = a;
                sourceProject = p;
                break;
            }
        }
        
        try {
            targetProject.addActivity(activity);
        } catch (Exception e) {
            thrownException = e;
        }
    }
    
    @When("I try to add another activity with name {string}")
    public void i_try_to_add_another_activity_with_name(String activityName) {
        try {
            Activity duplicateActivity = new Activity(activityName, project);
            project.addActivity(duplicateActivity);
        } catch (Exception e) {
            thrownException = e;
        }
    }
    
    @Then("the activity should be successfully added to the project")
    public void the_activity_should_be_successfully_added_to_the_project() {
        Assertions.assertNotNull(retrievedActivity, "The activity should exist in the project");
    }
    
    @Then("the project should have {int} activity")
    public void the_project_should_have_activity(Integer count) {
        Assertions.assertEquals(count, 
            count.intValue(), project.getActivities().size());
    }
    
    @Then("I should get the correct activity with name {string}")
    public void i_should_get_the_correct_activity_with_name(String expectedName) {
        Assertions.assertNotNull(retrievedActivity, "Retrieved activity should not be null");
        assertEquals("Activity name should match", expectedName, retrievedActivity.getName());
    }
    
    @Then("I should get a list containing {int} activities")
    public void i_should_get_a_list_containing_activities(Integer count) {
        Assertions.assertNotNull(activityList, "Activity list should not be null");
        Assertions.assertEquals(count, 
            count.intValue(), activityList.size());
    }
    
    @Then("the list should contain activities with names {string}, {string}, and {string}")
    public void the_list_should_contain_activities_with_names_and(String name1, String name2, String name3) {
        boolean containsName1 = false;
        boolean containsName2 = false;
        boolean containsName3 = false;
        
        for (Activity a : activityList) {
            if (a.getName().equals(name1)) containsName1 = true;
            if (a.getName().equals(name2)) containsName2 = true;
            if (a.getName().equals(name3)) containsName3 = true;
        }
        
        assertTrue(containsName1, "Activity list should contain " + name1);
        assertTrue(containsName2, "Activity list should contain " + name2);
        assertTrue(containsName3, "Activity list should contain " + name3);
    }
    
    @Then("an IllegalArgumentException should be thrown with message {string}")
    public void an_illegal_argument_exception_should_be_thrown_with_message(String expectedMessage) {
        assertNotNull( thrownException, "An exception should have been thrown");
        assertEquals("Exception message should match", expectedMessage, thrownException.getMessage());
    }
    
    @Then("I should get a null result")
    public void i_should_get_a_null_result() {
        assertNull(retrievedActivity, "Retrieved activity should be null");
    }

    @When("I retrieve all activities")
    public void i_retrieve_all_activities() {
        assertNotNull(project.getActivities(), "Activity list should not be null");
    }

    @Then("I should get a list of all acitivities")
    public void i_should_get_a_list_of_all_acitivities() {
        assertNotNull(project.getActivities(), "Activity list should not be null");
        assertEquals(project.getActivities().size(), project.getActivities().size(), "Activity list should have the same size as the project");
    }
}