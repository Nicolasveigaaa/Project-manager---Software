// [Written by s246060 ] //

package domain;

public class User {
    private final String initials;

    public User(String initials ) {
        this.initials = initials.toLowerCase(); // lowercase to combat case sensitivity
    }

    public String getInitials() {
        return initials;
    }
}
