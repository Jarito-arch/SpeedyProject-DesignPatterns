package SpeedyProject.Patterns.Singlenton;

import SpeedyProject.Models.User;

public class Session {
    //Singleton

    private Session instance;
    private User user;

    public Session getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
