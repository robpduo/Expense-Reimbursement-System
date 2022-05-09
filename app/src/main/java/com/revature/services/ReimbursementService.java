package com.revature.services;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.ReimbursementDao;
import com.revature.exceptions.NegativeAmountException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ReimbursementService {

    private IReimbursementDao rd = new ReimbursementDao();

    public ReimbursementService(IReimbursementDao rd) { this.rd = rd; }

    /**
     * View the past tickets from a user
     * @param u The user to view past tickets of
     * @return A list of the user's past tickets
     */
    public List<Reimbursement> viewPastTickets(User u) {
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> all = rd.readReimbursements();

        for (Reimbursement r : all) {
            if (!r.getStatus().equals(Status.PENDING) && r.getAuthor().getUserId() == u.getUserId()) {
                result.add(r);
            }
        }

        return result;
    }

    /**
     * View pending tickets
     * @param u The user to view pending transactions
     * @return A list of the user's pending tickets
     */
    public List<Reimbursement> viewPendingTickets(User u) {
        List<Reimbursement> result = new LinkedList<>();
        List<Reimbursement> all = rd.readReimbursements();

        for (Reimbursement r : all) {
            if (r.getStatus().equals(Status.PENDING) && r.getAuthor().getUserId() == u.getUserId()) {
                result.add(r);
            }
        }

        return result;
    }

    /**
     *
     * @param amount sum of expense
     * @param description
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

    public void updateRequest (User u, int id, Status status) throws UnauthorizedUserException {

        if (!u.getRole().equals(Role.MANAGER)) throw new UnauthorizedUserException();
            rd.updateReimbursement(id, "reimbursement_resolver", u.getUserId());
            rd.updateReimbursement(id, "reimbursement_status", Status.toInt(status));
            rd.updateReimbursement(id, "resolved_date", Date.valueOf(LocalDate.now()));

    }

}
