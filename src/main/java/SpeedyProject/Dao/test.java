package SpeedyProject.Dao;

import SpeedyProject.Models.User;

public class test {
    public static void main(String[] args) {
        //test login
        UserDao dao = new UserDaoImp();
        User user = dao.login("jaris.alday10@gmail.com", "1004");

        if(user!=null){
            System.out.println("Welcome "+ user.getName());
        }else {
            System.out.println("you dont exist");
        }
        //test register
        User user1 = new User();

        user1.setName("Henckel Alexander");
        user1.setEmail("HenckelAlex@gmail.com");
        user1.setPassword("2511");

        boolean register = dao.register(user1);

        System.out.println(register);

    }
}
