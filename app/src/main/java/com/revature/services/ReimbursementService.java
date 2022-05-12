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
     * @param username The username of the user who made the request
     * @param type type of expense
     */
    public void submitRequest (double amount, String description, String username, Type type) throws NegativeAmountException {
        Reimbursement r = new Reimbursement();

        if (amount < 0) throw new NegativeAmountException();

        IUserDao ud = new UserDao();
        UserService us = new UserService(ud);
        User author = us.getUserByUsername(username);

        r.setAmount(amount);
        r.setSumbmittedDate(LocalDate.now());
        r.setResolvedDate(null);
        r.setDescription(description);
        r.setAuthor(author);
        r.setStatus(Status.PENDING);
        r.setType(type);

        rd.createReimbursement(r);
    }

    /**
     * Retrieves all past tickets from a specific user
     * @param username The username of the user whose past tickets are being viewed
     * @return A list of the user's past tickets
     */
    public List<Reimbursement> viewPastTickets(String username) {
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();

        for (Reimbursement r : fullList) {
            if ((r.getStatus().equals(Status.APPROVED) || r.getStatus().equals(Status.DENIED))
                    && r.getAuthor().getUsername().equals(username)) result.add(r);
        }

        LoggingUtil.logger.info("Successfully retrieved all past tickets from user " + username);
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
     * @param username The username of the user updating the request
     * @param id The id of the request
     * @param status The new status for the request
     * @throws UnauthorizedUserException Only a manager can update the status
     */
    public void updateRequest (String username, int id, Status status)
            throws UnauthorizedUserException, IncorrectUsernameOrPasswordException {
        User u = getUserByUsername(username);
        if (!u.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to update Reimbursement #" + id + " failed");
            throw new UnauthorizedUserException();
        }
        rd.updateReimbursement(id, "reimbursement_resolver", u.getUserId());
        rd.updateReimbursement(id, "reimbursement_status", Status.toInt(status));
        rd.updateReimbursement(id, "resolved_date", Date.valueOf(LocalDate.now()));
        LoggingUtil.logger.info("Updated Reimbursement #" + id);
    }

    /**
     * viewAllPending: Views all pending tickets from all employees
     * @param username The user viewing the tickets (must be a manager)
     * @return a list of all pending requests from all employees
     * @throws UnauthorizedUserException Only a manager can view all pending tickets
     */
    public List<Reimbursement> viewAllPending(String username)
            throws UnauthorizedUserException, IncorrectUsernameOrPasswordException {
        User u = getUserByUsername(username);
        if (!u.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to view pending tickets failed");
            throw new UnauthorizedUserException();
        }
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();
        for (Reimbursement r : fullList) {
            if (r.getStatus().equals(Status.PENDING)) result.add(r);
        }
        LoggingUtil.logger.info("Successfully retrieved all pending tickets");
        return result;
    }

    /**
     * viewAllResolved: Views all resolved tickets from all employees
     * @param username The username of the user viewing their resolved tickets
     * @return A list of all resolved tickets
     * @throws UnauthorizedUserException Only a manger can view all resolved tickets
     */
    public List<Reimbursement> viewAllResolved(String username)
            throws UnauthorizedUserException, IncorrectUsernameOrPasswordException {
        User u = getUserByUsername(username);
        if (!u.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to view all resolved tickets failed");
            throw new UnauthorizedUserException();
        }
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();
        for (Reimbursement r : fullList) {
            if (r.getStatus().equals(Status.APPROVED) || r.getStatus().equals(Status.DENIED)) result.add(r);
        }
        LoggingUtil.logger.info("Successfully retrieved all resolved tickets");
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
        if (!manager.getRole().equals(Role.MANAGER)) {
            LoggingUtil.logger.info("Attempt to retrieve requests from Employee #" + emp.getUserId() + " failed");
            throw new UnauthorizedUserException();
        }
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> fullList = rd.readReimbursements();
        for (Reimbursement r : fullList) {
            if (r.getAuthor().getUserId() == emp.getUserId()) result.add(r);
        }
        LoggingUtil.logger.info("Successfully retrieved requests from Employee #" + emp.getUserId());
        return result;
    }

    /**
     * Gets a user by their username
     * @param username The user's username
     * @return The user with the specified username
     */
    public User getUserByUsername(String username) throws IncorrectUsernameOrPasswordException {
        IUserDao ud = new UserDao();
        UserService us = new UserService(ud);
        User u = ud.getUserByUsername(username);
        if (u == null) throw new IncorrectUsernameOrPasswordException();
        return u;
    }

}
