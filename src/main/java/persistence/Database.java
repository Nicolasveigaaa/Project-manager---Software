package persistence;

// Folder imports
import domain.Employee;

// Java utilities
import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Map<String, Employee> allowedUsers = new HashMap<>();

    public Database() {
        // Hardcoded users — just add manually here
        addUser(new Employee("huba", "huba123"));
        addUser(new Employee("nico", "nico123"));
        addUser(new Employee("admin", "admin"));
    }

    public void addUser(Employee employee) {
        allowedUsers.put(employee.getInitials(), employee);
    }

    public Employee getUser(String initials) {
        return allowedUsers.get(initials);
    }

    public Map<String, Employee> getAllUsers() {
        return new HashMap<>(allowedUsers);
    }
}