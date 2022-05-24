package com.revature.services;

import com.revature.dao.IUserDao;
import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.utils.LoggingUtil;

import java.util.LinkedList;
import java.util.List;

public class UserService {

    private IUserDao ud;

    public UserService(IUserDao uDao) { this.ud = uDao; }

    /**
     * Logs in a user
     * @param username The user's username
     * @param password The user's password
     * @return The logged in user
     * @throws IncorrectUsernameOrPasswordException
     */
    public User loginUser(String username, String password) throws IncorrectUsernameOrPasswordException {
        User u = ud.getUserByUsername(username);
        if (u == null || !password.equals(u.getPassword())) {
            LoggingUtil.logger.info("Attempt to login as user " + username + " failed");
            throw new IncorrectUsernameOrPasswordException();
        }
        return u;
    }

    public void updateUser(User u) {
        if (ud.updateUser(u) == false) {
            LoggingUtil.logger.info("Failed to update user " + u.getUserId());
            throw new NullPointerException();
        }
    }

    public void registerUser(User u) throws ExistingUserException {
        if(ud.getUserByUsername(u.getUsername()) == null) {
            LoggingUtil.logger.info("Failed to register user " + u.getUserId());
            throw new ExistingUserException();
        }
        ud.createUser(u);
    }

    /**
     * viewAllEmployees: View all employees
     * @param u The manager viewing the employees
     * @return A list of all employees
     * @throws UnauthorizedUserException Only a manager can view all employees
     */
    public List<User> viewAllEmployees(User u) throws UnauthorizedUserException {
        if (!u.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to retrieve all Employees failed");
            throw new UnauthorizedUserException();
        }
        List<User> result = new LinkedList<>();
        List<User> allUsers = ud.readUsers();
        for (User user : allUsers) {
            if (user.getRole().equals(Role.EMPLOYEE)) result.add(user);
        }
        return result;
    }

    /**
     * Gets a user by their username
     * @param username The user's username
     * @return The user
     */
    public User getUserByUsername(String username) {
        return ud.getUserByUsername(username);
    }


}
