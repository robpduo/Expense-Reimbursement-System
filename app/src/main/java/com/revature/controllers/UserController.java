package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
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

    public Handler getUser = (ctx) -> {
        User u = om.readValue(ctx.body(), User.class);

        try {
             ctx.result(om.writeValueAsString(us.getUserByUsername(u.getUsername())));
             if(us.getUserByUsername(u.getUsername()) ==null) {
                 System.out.println("NULL USER");
                 ctx.status(401);
             }
        } catch (NullPointerException e) {
            ctx.result("Incorrect Username or Password");
            ctx.status(401);
        }
    };

    public Handler handleLogin = (ctx) -> {
        User u = om.readValue(ctx.body(), User.class);
        try {
            User login = us.loginUser(u.getUsername(), u.getPassword());

            ctx.result(om.writeValueAsString(login));
            ctx.status(200);

            ctx.req.getSession().setAttribute("LoggedIn", login.getUsername());
            LoggingUtil.logger.info("Successfully logged in user " + login.getUserId());

            LoggingUtil.logger.info("Successfully logged in user " + login.getUserId());

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

    public Handler handleUserUpdate = (ctx) -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");

        User u = us.getUserByUsername(username);
        User editUser = om.readValue(ctx.body(), User.class);

        u.setUsername(editUser.getUsername());
        u.setEmail(editUser.getEmail());
        u.setfName(editUser.getfName());
        u.setlName(editUser.getlName());

        if (editUser.getPassword() != null) {
            u.setPassword(editUser.getPassword());
        }

        try {
            us.updateUser(u);
            ctx.req.getSession().setAttribute("LoggedIn", u.getUsername());
            ctx.result(om.writeValueAsString(u));
            ctx.status(201);
            LoggingUtil.logger.info("Successfully Updated User: " + u.getUserId());
        } catch (NullPointerException e) {
            ctx.result("Incorrect Username or Password");
            ctx.status(401);
        }
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
