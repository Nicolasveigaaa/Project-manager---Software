package hellocucumber.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.Project;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectGeneral {
    private Project project = new Project("TEST");
    private String originalLeaderInitials;
    private String retrievedLeaderInitials;
    private List<String> retrievedMemberInitials;
    private Exception thrownException;

    @Given("the project leader initials are set to {string}")
    public void the_project_leader_initials_are_set_to(String leaderInitials) {
        project.setProjectLeaderInitials(leaderInitials);
    }

    @When("I set the project leader initials to {string}")
    public void i_set_the_project_leader_initials_to(String leaderInitials) {
        project.setProjectLeaderInitials(leaderInitials);
    }

    @When("I update the project leader initials to {string}")
    public void i_update_the_project_leader_initials_to(String leaderInitials) {
        originalLeaderInitials = project.getProjectLeaderInitials();
        project.setProjectLeaderInitials(leaderInitials);
    }

    @When("I try to set the project leader initials to null")
    public void i_try_to_set_the_project_leader_initials_to_null() {
        originalLeaderInitials = project.getProjectLeaderInitials();
        try {
            project.setProjectLeaderInitials(null);
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @When("I retrieve the project leader initials")
    public void i_retrieve_the_project_leader_initials() {
        retrievedLeaderInitials = project.getProjectLeaderInitials();
    }

    @When("I retrieve the project member initials")
    public void i_retrieve_the_project_member_initials() {
        retrievedMemberInitials = project.getMemberInitials();
    }

    @Then("the project leader initials should be {string}")
    public void the_project_leader_initials_should_be(String expectedInitials) {
        assertEquals("Project leader initials should match", expectedInitials, project.getProjectLeaderInitials());
    }

    @Then("the project leader initials should remain unchanged")
    public void the_project_leader_initials_should_remain_unchanged() {
        assertEquals("Project leader initials should remain unchanged",
                originalLeaderInitials, project.getProjectLeaderInitials());
    }

    @Then("I should get {string} as the project leader initials")
    public void i_should_get_as_the_project_leader_initials(String expectedInitials) {
        assertNotNull("Retrieved leader initials should not be null", retrievedLeaderInitials);
        assertEquals("Retrieved leader initials should match", expectedInitials, retrievedLeaderInitials);
    }

    @Given("the project has no members")
    public void the_project_has_no_members() {
        retrievedMemberInitials = new ArrayList<>();
    }

    @Then("I should get an empty list of member initials")
    public void i_should_get_an_empty_list_of_member_initials() {
        assertNotNull(retrievedMemberInitials, "Member initials list should not be null");
        assertTrue(retrievedMemberInitials.isEmpty(), "Member initials list should be empty");
    }

    @Then("I should get a list containing {int} member initials")
    public void i_should_get_a_list_containing_member_initials(Integer count) {
        assertNotNull(retrievedMemberInitials, "Member initials list should not be null");
        assertEquals(count,
                count.intValue(), retrievedMemberInitials.size());
    }

    @Given("the project has the following members:")
    public void the_project_has_the_following_members(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        // This implementation assumes there's a method to add members to a project
        // If your Project class has a different way to add members, this will need to
        // be updated
        List<String> members = new ArrayList<>();
        for (Map<String, String> row : rows) {
            members.add(row.get("initials"));
        }

        // Mock implementation - assuming there's a setMemberInitials method
        // Replace this with your actual implementation
        setMemberInitials(project, members);
    }

    @Then("the list should include the member initials {string}, {string}, and {string}")
    public void the_list_should_include_the_member_initials_and(String initials1, String initials2, String initials3) {
        System.out.println("HELLO THERE: " + retrievedMemberInitials);
        assertTrue(retrievedMemberInitials.contains(initials1));
        assertTrue(retrievedMemberInitials.contains(initials2));
        assertTrue(retrievedMemberInitials.contains(initials3));
    }

    // Helper method - replace with your actual implementation
    private void setMemberInitials(Project project, List<String> memberInitials) {
        for (String initials : memberInitials) {
            project.addMember(initials);
        }
    }
}
