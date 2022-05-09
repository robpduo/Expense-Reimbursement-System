package com.revature;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.IUserDao;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.models.*;
import com.revature.services.ReimbursementService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static IUserDao ud = new UserDao();
    public static IReimbursementDao rd = new ReimbursementDao();
    public static ReimbursementService rs = new ReimbursementService(rd);

    public static void main(String[] args) {

        /*
        List<User> users = ud.readUsers();
        User u1 = users.get(0);
        User u2 = users.get(1);
        LocalDate date1 = LocalDate.now();
        Reimbursement r1 = new Reimbursement(400.0, date1, date1, "new reimbursement", u1,u2, Status.PENDING, Type.FOOD);

         */

        // rd.createReimbursement(r1);

        /*
        List<Reimbursement> reimbursements = rd.readReimbursements();


         */

        // rd.updateReimbursement(1, "reimbursement_status", Status.toInt(Status.RESOLVED));
        // rd.deleteReimbursement(1);

        List<Reimbursement> reimbursements = rs.viewPastTickets(ud.getUserById(2));

        if (reimbursements.size() == 0) System.out.println("nothing");

        for (Reimbursement r : reimbursements) System.out.println(r.toString());


    }
}
