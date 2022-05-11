package com.revature;

import com.revature.controllers.ReimbursementController;
import com.revature.controllers.UserController;
import com.revature.dao.IReimbursementDao;
import com.revature.dao.IUserDao;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.exceptions.NegativeAmountException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.*;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.put;


public class Driver {
    public static IReimbursementDao rd = new ReimbursementDao();
    public static ReimbursementService rs = new ReimbursementService(rd);
    public static ReimbursementController rCon = new ReimbursementController(rs);

    public static IUserDao ud = new UserDao();
    public static UserService us = new UserService(ud);

    public static UserController uCon = new UserController(us);

    public static void main(String[] args) throws UnauthorizedUserException {

        Javalin server = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        });

        server.exception(IncorrectUsernameOrPasswordException.class, (exception, ctx) -> {
            ctx.result("Username or password was incorrect");
        });

        server.exception(NegativeAmountException.class, (exception, ctx) -> {
            ctx.result("Amount cannot be negative");
        });

        server.exception(UnauthorizedUserException.class, (exception, ctx) -> {
            ctx.result("You are not authorized to perform this action");
        });

        server.routes( () -> {
           path("users", () -> {
              post("/login", uCon.handleLogin);
              post("/register", uCon.handleRegisterUser);
              get("/viewAllEmployees", uCon.handleViewALlEmployees);
           });
           path("reimbursements", () -> {
               post("/submit", rCon.handleSubmitRequest);
               get("/viewPast", rCon.handleViewPastTickets);
               get("/viewPending", rCon.handleViewPendingTickets);
               put("/update", rCon.handleUpdateRequest);
               get("/viewAllPending", rCon.handleViewAllPending);
               get("/viewAllResolved", rCon.handleViewAllResolved);
           });

        });

        server.start(8000);


    }
}
