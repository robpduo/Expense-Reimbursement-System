package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.utils.LoggingUtil;
import com.sun.javafx.util.Logging;
import io.javalin.http.Handler;

public class ReimbursementController {

    private ReimbursementService rs;
    private ObjectMapper om;

    public ReimbursementController(ReimbursementService rs) {
        this.rs = rs;
        this.om = new ObjectMapper();
    }

    public Handler handleSubmitRequest = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            LoggingUtil.logger.info("Failed attempt to submit reimbursement request");
            ctx.status(401);
            ctx.result("You must be logged in to submit a reimbursement request");
        } else {
            Reimbursement r = om.readValue(ctx.body(), Reimbursement.class);
            rs.submitRequest(r.getAmount(), r.getDescription(), username, r.getType());
            LoggingUtil.logger.info("User " + username + "Successfully submitted a new reimbursement request");
            ctx.status(200);
            ctx.result("Request submitted");
        }
    };

    public Handler handleViewPastTickets = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            LoggingUtil.logger.info("Failed attempt to view past tickets");
            ctx.status(401);
            ctx.result("You must be logged in view your past tickets");
        } else {
            ctx.status(200);
            om.registerModule(new JavaTimeModule());
            ctx.result(om.writeValueAsString(rs.viewPastTickets(username)));
        }
    };

    public Handler handleViewPendingTickets = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            LoggingUtil.logger.info("Failed attempt to view pending tickets");
            ctx.status(401);
            ctx.result("You must be logged in to view your pending tickets");
        } else {
            ctx.status(200);
            om.registerModule(new JavaTimeModule());
            ctx.result(om.writeValueAsString(rs.viewPendingTickets(username)));
        }

    };

    public Handler handleUpdateRequest = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            LoggingUtil.logger.info("Failed attempt to update reimbursement request");
            ctx.status(401);
            ctx.result("You must be logged in to update a reimbursement request");
        } else {
            Reimbursement r = om.readValue(ctx.body(), Reimbursement.class);
            rs.updateRequest(username, r.getId(), r.getStatus());
            ctx.status(200);
            LoggingUtil.logger.info(r.getStatus() + " Reimbursement #" + r.getId());
            ctx.result("Reimbursement #" + r.getId() + " has been " + r.getStatus());
        }
    };

    public Handler handleViewAllPending = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            LoggingUtil.logger.info("Failed attempt to view all pending requests");
            ctx.status(401);
            ctx.result("You must be logged in to view all pending requests");
        } else {
            ctx.status(200);
            om.registerModule(new JavaTimeModule());
            ctx.result(om.writeValueAsString(rs.viewAllPending(username)));
        }
    };

    // handle viewAllResolved
    public Handler handleViewAllResolved = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");
        if (username == null) {
            LoggingUtil.logger.info("Failed attempt to view all resolved requests");
            ctx.status(401);
            ctx.result("You must be logged in to view all resolved requests");
        } else {
            ctx.status(200);
            om.registerModule(new JavaTimeModule());
            ctx.result(om.writeValueAsString(rs.viewAllResolved(username)));
        }
    };

    public Handler handleViewEmployeeRequests = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("LoggedIn");

        if (username == null) {
            LoggingUtil.logger.info("Failed attempt to view all employee requests");
            ctx.status(401);
            ctx.result("You must be logged in to view all employee requests");
        } else {
            User u = om.readValue(ctx.body(), User.class);
            ctx.status(200);
            ctx.result(om.writeValueAsString(rs.viewEmployeeRequests(username, u.getUsername())));
        }
    };


}