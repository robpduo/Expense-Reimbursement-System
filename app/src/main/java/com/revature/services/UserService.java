package com.revature.services;

import com.revature.dao.UserDao;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.models.Reimbursement;
import com.revature.models.User;

import java.util.LinkedList;
import java.util.List;

public class UserService {

    private UserDao ud;

    public UserService(UserDao uDao) { this.ud = uDao; }

    /**
     * Logs in a user
     * @param username The user's username
     * @param password The user's password
     * @return The logged in user
     * @throws IncorrectUsernameOrPasswordException
     */
    public User loginUser(String username, String password) throws IncorrectUsernameOrPasswordException {
        User u = ud.getUserByUsername(username);
        if (ud.getUserByUsername(username) == null || !password.equals(u.getPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }
        return u;
    }


}
