package app.employee;

public class RoleChecker {
    public static boolean isManager(String role) {
        return "manager".equalsIgnoreCase(role);
    }

    public static boolean isEmployee(String role) {
        return "employee".equalsIgnoreCase(role);
    }

    public static boolean isValidRole(String role) {
        return isManager(role) || isEmployee(role);
    }
}
