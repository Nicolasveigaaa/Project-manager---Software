package domain;

public class Project {
    private String projectID;
    private String projectName;
    private String projectManager;

    public Project(String projectID) {
        this.projectID = projectID;
        this.projectName = projectID;
        this.projectManager = "Not assigned";
    }
}
