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

        } catch (IncorrectUsernameOrPasswordException e) {
            ctx.result("Incorrect Username or Password");
            ctx.status(401);
        }
    };

    public Handler handleLogout = (ctx) -> {
        ctx.req.getSession().removeAttribute("LoggedIn");
        ctx.status(200);
        ctx.result("Logged out Successfully");
    };

    public Handler handleRegisterUser = (ctx) -> {
        User u = om.readValue(ctx.body(), User.class);

        try {
            us.registerUser(u);
            ctx.result("Username: " + u.getUsername() + " Successfully Created");
            ctx.status(201);
        } catch (ExistingUserException e) {
            ctx.status(409);
            ctx.result("User already exists");
        }
    };

    public Handler handleViewALlEmployees = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            LoggingUtil.logger.info("Failed attempt to view all employees");
            ctx.status(401);
            ctx.result("You must be logged in to view all employees");
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
            LoggingUtil.logger.info("Failed attempt to view account information");
            ctx.status(401);
            ctx.result("You must be logged in to view account information");
        } else {
            ctx.status(200);
            ctx.result(om.writeValueAsString(us.getUserByUsername(username)));
            LoggingUtil.logger.info(username + " successfully retrieved account information");
        }
    };
}
