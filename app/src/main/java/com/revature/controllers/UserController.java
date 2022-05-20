package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.services.UserService;
import com.revature.utils.LoggingUtil;
import io.javalin.http.Handler;
import com.revature.models.User;

public class UserController {

    private UserService us;
    private ObjectMapper om;

    public UserController(UserService us) {
        this.us = us;
        this.om = new ObjectMapper();
    }

    public Handler handleLogin = (ctx) -> {
        User u = om.readValue(ctx.body(), User.class);
        try {
            User login = us.loginUser(u.getUsername(), u.getPassword());

            ctx.result(om.writeValueAsString(login));
            ctx.status(200);

            ctx.req.getSession().setAttribute("LoggedIn", login.getUsername());
<<<<<<< HEAD

            LoggingUtil.logger.info("Successfully logged in user " + login.getUserId());

=======
            System.out.println(ctx.req.getSession().getAttribute("LoggedIn").toString());
>>>>>>> c3b3c9e (iunno)
        } catch (IncorrectUsernameOrPasswordException e) {
            ctx.result("Incorrect Username or Password");
            ctx.status(401);
        }
    };

    public Handler handleLogout = (ctx) -> {
        ctx.req.getSession().removeAttribute("LoggedIn");
        ctx.status(200);
        ctx.result("Logged out Successfully");
        LoggingUtil.logger.info("Successfully logged out");
    };

    public Handler handleRegisterUser = (ctx) -> {
        User u = om.readValue(ctx.body(), User.class);

        try {
            us.registerUser(u);
            ctx.result("Username: " + u.getUsername() + " Successfully Created");
            ctx.status(201);
            LoggingUtil.logger.info("Successfully registered user " + u.getUserId());
        } catch (ExistingUserException e) {
            ctx.status(409);
            ctx.result("User already exists");
        }
    };

    public Handler handleViewALlEmployees = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            ctx.status(401);
            ctx.result("You must be logged in to view all employees");
            LoggingUtil.logger.info("Failed attempt to view all employees");
        } else {
            User u = us.getUserByUsername(username);
            ctx.status(200);
            ctx.result(om.writeValueAsString(us.viewAllEmployees(u)));
            LoggingUtil.logger.info(username + " successfully viewed all employees");
        }
    };

    public Handler handleViewAccountInfo = ctx -> {

        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            ctx.status(401);
            ctx.result("You must be logged in to view account information");
            LoggingUtil.logger.info("Failed attempt to view account information");
        } else {
            ctx.status(200);
            ctx.result(om.writeValueAsString(us.getUserByUsername(username)));
            LoggingUtil.logger.info(username + " successfully retrieved account information");
        }
    };
}
