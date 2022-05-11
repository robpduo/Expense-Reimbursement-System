package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.services.UserService;
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

            ctx.result("Logged in as: " + u.getUsername());
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
            ctx.status(409); //TO DO
            ctx.result("User already exists");
        }
    };




}
