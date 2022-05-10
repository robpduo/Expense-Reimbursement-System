package com.revature;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.IUserDao;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.*;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

public class Driver {
    public static IReimbursementDao rd = new ReimbursementDao();
    public static ReimbursementService rs = new ReimbursementService(rd);

    public static IUserDao ud = new UserDao();
    public static UserService us = new UserService(ud);

    public static void main(String[] args) throws UnauthorizedUserException {

        User manager = ud.getUserById(2);
        User employee = ud.getUserById(3);

        rs.updateRequest(manager, 2, Status.PENDING);

    }
}
