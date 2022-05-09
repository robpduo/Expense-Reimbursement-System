package com.revature.dao;
import com.revature.models.User;

import java.util.List;

public interface IUserDao {

    public boolean createUser(User user);

    public User getUserById(int id);

    public User getUserByUsername(String username);

    public List<User> readUsers();

    public boolean updateUser(User user);

    public boolean updateUser(User user, String column, int nValue);

    public void deleteUser(User user);


}
