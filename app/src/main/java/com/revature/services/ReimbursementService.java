package com.revature.services;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.ReimbursementDao;
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
     * @param author user who made the request
     * @param type type of expense
     */
    public void submitRequest (double amount, String description, User author, Type type) throws NegativeAmountException {
        Reimbursement r = new Reimbursement();

        if (amount < 0) throw new NegativeAmountException();

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
     * Updates the status of a reimbursement request
     * @param u The user updating the request
     * @param id The id of the request
     * @param status The new status for the request
     * @throws UnauthorizedUserException Only a manager can update the status
     */
    public void updateRequest (User u, int id, Status status) throws UnauthorizedUserException {

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
     * @param u The user viewing the tickets (must be a manager)
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
        LoggingUtil.logger.info("Successfully retrieved all pending tickets");
        return result;
    }

    /**
     * viewAllResolved: Views all resolved tickets from all employees
     * @param u The user viewing all the resolved tickets
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
        LoggingUtil.logger.info("Successfull retrieved all resolved tickets");
        return result;
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
        LoggingUtil.logger.info("Successfully retrieved requests from Employee #" + emp.getUserId());
        return result;
    }

}
