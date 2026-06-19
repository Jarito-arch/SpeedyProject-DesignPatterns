package SpeedyProject.Dao;

import SpeedyProject.Models.User;

public class test {
    public static void main(String[] args) {
        UserDao dao = new UserDaoImp();
        User user = dao.login("jaris.alday10@gmail.com", "1004");

        if(user!=null){
            System.out.println("Welcome "+ user.getName());
        }else {
            System.out.println("you dont exist");
        }
    }
}
