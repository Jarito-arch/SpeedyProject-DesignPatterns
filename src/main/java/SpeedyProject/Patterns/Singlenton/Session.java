package SpeedyProject.Patterns.Singlenton;
import SpeedyProject.Models.User;

public class Session {
    //should be static because the variable
    // belongs to a class not and object
    private static Session instance;
    private User currentUser;

    //always has to be private
    //we don't want anyone to call it through "new"
    private Session(){}

    //if they call the instance
    // and it's not created
    // it'll use the new, otherwise it will just return the instance
    public Session getInstance() {
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
