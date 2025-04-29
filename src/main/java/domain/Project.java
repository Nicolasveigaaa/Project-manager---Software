package domain;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private final String projectName;
    private final List<String> memberInitials;

    public Project(String projectName) {
        this.projectName     = projectName;
        this.memberInitials  = new ArrayList<>();
    }

    public Project(String projectName, List<String> memberInitials) {
        this.projectName    = projectName;
        this.memberInitials = new ArrayList<>(memberInitials);
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public String toString() {
        return projectName + " (" + memberInitials.size() + " members)";
    }
}