package com.revature;

import com.revature.dao.IUserDao;
import com.revature.dao.UserDao;
import com.revature.models.Role;
import com.revature.models.User;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static IUserDao ud = new UserDao();

    public static void main(String[] args) {

        /*case 1*/
        User u1 = new User(1, "EMcgill", "password", "Ethan", "McGill", "Ethan.Mcgill@revature.com", Role.MANAGER);
        //ud.createUser(u1);

        /*case 2*/
        List<User> users = new ArrayList<>();
        users = ud.readUsers();

        for (User entry : users) {
            System.out.println(entry.toString());
        }

        /*case 3*/
        ud.updateUser(u1, "password", "abc");



    }
}
