package com.revature.dao;
import com.revature.models.User;

import java.util.List;

public interface IUserDao {

    /*Inserts user information into database*/
    public boolean createUser(User user);

    public List<User> readUsers();

    public boolean updateUser(User user, String column, String nValue);

    public void deleteUser(User user);


}
