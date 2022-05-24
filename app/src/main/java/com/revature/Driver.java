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
            ctx.result("Could not find account with the given information");
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
              get("/view-all-employees", uCon.handleViewALlEmployees);
              put("/logout", uCon.handleLogout);
              get("/view-account", uCon.handleViewAccountInfo);
              post("/get-user", uCon.getUser);
              post("/update", uCon.handleUserUpdate);
           });



           
           path("reimbursements", () -> {
               post("/submit", rCon.handleSubmitRequest);
               get("/view-past", rCon.handleViewPastTickets);
               get("/view-pending", rCon.handleViewPendingTickets);
               put("/update", rCon.handleUpdateRequest);
               get("/view-all-pending", rCon.handleViewAllPending);
               get("/view-all-resolved", rCon.handleViewAllResolved);
               get("/view-employee-requests", rCon.handleViewEmployeeRequests);
           });
        });
        server.before(ctx -> ctx.header("Access-Control-Allow-Credentials", "true"));
        server.before(ctx -> ctx.header("Access-Control-Expose-Headers", "*"));

        server.start(8000);


    }
}
