package com.revature.services;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;

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
}
