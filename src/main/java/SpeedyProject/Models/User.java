package SpeedyProject.Models;

import java.util.List;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    //
    private ShoppingCart shoppingCart;
    private List<Order> orders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User(){};

    public User(int id, String name, String password, String email){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void login(){

    }
    public void logout(){

    }
}
