package com.revature.services;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.IUserDao;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.exceptions.NegativeAmountException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.*;
import com.revature.utils.LoggingUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ReimbursementService {

    private IReimbursementDao rd = new ReimbursementDao();

    public ReimbursementService(IReimbursementDao rd) { this.rd = rd; }

    /**
     * Submits a reimbursement request
     * @param amount sum of expense
     * @param description the reimbursement's description
     * @param author The user who made the request
     * @param type type of expense
     */
    public void submitRequest(double amount, String description, User author, Type type) throws NegativeAmountException {
        Reimbursement r = new Reimbursement();
        if (amount < 0) {
            LoggingUtil.logger.info("Could not submit request due to negative amount");
            throw new NegativeAmountException();
        }

        r.setAmount(amount);
        r.setSubmittedDate(LocalDate.now());
        r.setResolvedDate(null);
        r.setDescription(description);
        r.setAuthor(author);
        r.setStatus(Status.PENDING);
        r.setType(type);

        rd.createReimbursement(r);
    }

    /**
     * Submits a reimbursement request
     * @param amount sum of expense
     * @param description the reimbursement's description
     * @param username The username of the user who made the request
     * @param type type of expense
     */
    public void submitRequest (double amount, String description, String username, Type type)
            throws NegativeAmountException, IncorrectUsernameOrPasswordException {
        User u = getUserByUsername(username);
        submitRequest(amount, description, u, type);
    }

    /**
     * Retrieves all past tickets from a specific user
     * @param username The username of the user viewing their past tickets
     * @return A list of the user's past tickets
     */
    public List<Reimbursement> viewPastTickets(String username) {
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();

        for (Reimbursement r : fullList) {
            if ((r.getStatus().equals(Status.APPROVED) || r.getStatus().equals(Status.DENIED))
                    && r.getAuthor().getUsername().equals(username)) result.add(r);
        }
        return result;
    }

    /**
     * Retrieves all pending tickets from a user
     * @param username The username of the user whose pending tickets to view
     * @return A list of the user's pending tickets
     */
    public List<Reimbursement> viewPendingTickets(String username) {
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();

        for (Reimbursement r : fullList) {
            if (r.getStatus().equals(Status.PENDING) && r.getAuthor().getUsername().equals(username)) result.add(r);
        }

        LoggingUtil.logger.info("Successfully retrieved all pending tickets from user " + username);
        return result;
    }

    /**
     * Updates the status of a reimbursement request
     * @param u The manager updating the request
     * @param id The id of the request
     * @param status The new status for the request
     * @throws UnauthorizedUserException Only a manager can update the status
     */
    public void updateRequest(User u, int id, Status status) throws UnauthorizedUserException {
        if (!u.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to update Reimbursement #" + id + " failed");
            throw new UnauthorizedUserException();
        }
        rd.updateReimbursement(id, "reimbursement_resolver", u.getUserId());
        rd.updateReimbursement(id, "reimbursement_status", Status.toInt(status));
        rd.updateReimbursement(id, "resolved_date", Date.valueOf(LocalDate.now()));
    }

    /**
     * Updates the status of a reimbursement request
     * @param username The username of the manager updating the request
     * @param id The id of the request
     * @param status The new status for the request
     * @throws UnauthorizedUserException Only a manager can update the status
     */
    public void updateRequest (String username, int id, Status status)
            throws UnauthorizedUserException, IncorrectUsernameOrPasswordException {
        User u = getUserByUsername(username);
        updateRequest(u, id, status);
    }

    /**
     * viewAllPending: Views all pending tickets from all employees
     * @param u The manager viewing the tickets
     * @return a list of all pending requests from all employees
     * @throws UnauthorizedUserException Only a manager can view all pending tickets
     */
    public List<Reimbursement> viewAllPending(User u) throws UnauthorizedUserException {
        if (!u.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to view pending tickets failed");
            throw new UnauthorizedUserException();
        }
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();
        for (Reimbursement r : fullList) {
            if (r.getStatus().equals(Status.PENDING)) result.add(r);
        }
        return result;
    }

    /**
     * viewAllPending: Views all pending tickets from all employees
     * @param username The username of the manager viewing the tickets
     * @return a list of all pending requests from all employees
     * @throws UnauthorizedUserException Only a manager can view all pending tickets
     */
    public List<Reimbursement> viewAllPending(String username)
            throws UnauthorizedUserException, IncorrectUsernameOrPasswordException {
        User u = getUserByUsername(username);
        LoggingUtil.logger.info("Successfully retrieved all pending tickets");
        return viewAllPending(u);
    }

    /**
     * viewAllResolved: Views all resolved tickets from all employees
     * @param u The manager viewing all resolved tickets
     * @return A list of all resolved tickets
     * @throws UnauthorizedUserException Only a manger can view all resolved tickets
     */
    public List<Reimbursement> viewAllResolved(User u) throws UnauthorizedUserException {
        if (!u.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to view all resolved tickets failed");
            throw new UnauthorizedUserException();
        }
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();
        for (Reimbursement r : fullList) {
            if (r.getStatus().equals(Status.APPROVED) || r.getStatus().equals(Status.DENIED)) result.add(r);
        }
        return result;
    }

    /**
     * viewAllResolved: Views all resolved tickets from all employees
     * @param username The username of the manager viewing all resolved tickets
     * @return A list of all resolved tickets
     * @throws UnauthorizedUserException Only a manger can view all resolved tickets
     */
    public List<Reimbursement> viewAllResolved(String username)
            throws UnauthorizedUserException, IncorrectUsernameOrPasswordException {
        User u = getUserByUsername(username);
        return viewAllResolved(u);
    }

    /**
     * viewEmployeeRequests: View all requests from a specific employee
     * @param manager The manager viewing the requests
     * @param emp The employee whose requests the manager is viewing
     * @return a list of an employee's requests
     * @throws UnauthorizedUserException Only a manager can view a specific employee's requests
     */
    public List<Reimbursement> viewEmployeeRequests(User manager, User emp) throws UnauthorizedUserException {
        if (!manager.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to retrieve requests from Employee #" + emp.getUserId() + " failed");
            throw new UnauthorizedUserException();
        }
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();
        for (Reimbursement r : fullList) {
            if (r.getAuthor().getUserId() == emp.getUserId()) result.add(r);
        }
        return result;
    }

    /**
     * viewEmployeeRequests: View all requests from a specific employee
     * @param m The manager viewing the requests
     * @param e The employee whose requests the manager is viewing
     * @return a list of an employee's requests
     * @throws UnauthorizedUserException Only a manager can view a specific employee's requests
     */
    public List<Reimbursement> viewEmployeeRequests(String m, String e)
            throws UnauthorizedUserException, IncorrectUsernameOrPasswordException {
        User manager = getUserByUsername(m);
        User emp = getUserByUsername(e);
        LoggingUtil.logger.info("Successfully retrieved requests from Employee #" + emp.getUserId());
        return viewEmployeeRequests(manager, emp);
    }

    /**
     * Gets a user by their username
     * @param username The user's username
     * @return The user with the specified username
     */
    public User getUserByUsername(String username) throws IncorrectUsernameOrPasswordException {
        IUserDao ud = new UserDao();
        UserService us = new UserService(ud);
        User u = us.getUserByUsername(username);
        if (u == null) {
            LoggingUtil.logger.info("IncorrectoUsernameOrPasswordException was thrown");
            throw new IncorrectUsernameOrPasswordException();
        }
        return u;
    }

}
