package com.revature.service;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.IUserDao;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.NegativeAmountException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.*;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ReimbursementServiceTest {

    @Mock
    public static ReimbursementDao rd;

    @InjectMocks
    public static ReimbursementService rs;

    @Before
    public void setupBeforeMethods() {
        MockitoAnnotations.openMocks(this);
    }

    // tests for viewPastTickets -------------------------------------------------------------------------

    // regular test
    public static IReimbursementDao realRd = new ReimbursementDao();
    public static ReimbursementService realRs = new ReimbursementService(realRd);

    @Test
    public void testViewPastTickets() {
        IUserDao ud = new UserDao();
        List<Reimbursement> testList = realRs.viewPastTickets(ud.getUserById(2));
        Assert.assertEquals(2, testList.size());
    }

    // tests for viewPendingTickets ----------------------------------------------------------------------

    @Test
    public void testViewPendingTickets() {
        IUserDao ud = new UserDao();
        List<Reimbursement> testList = realRs.viewPendingTickets(ud.getUserById(2));
        Assert.assertEquals(2, testList.size());

    }

    // tests for submitRequest ---------------------------------------------------------------------------

    @Test(expected = NegativeAmountException.class)
    public void testSubmitRequestNegativeAmount() throws NegativeAmountException {
        User u = new User();
        rs.submitRequest(-5, "hello world", u, Type.LODGING);
    }

    // tests for updateRequest ----------------------------------------------------------------------------

    @Test
    public void testUpdateRequest() throws UnauthorizedUserException {
        IUserDao ud = new UserDao();
        User testManager = ud.getUserById(3);
        rs.updateRequest(testManager, 9, Status.APPROVED);
    }

    @Test(expected = UnauthorizedUserException.class)
    public void testUpdateRequestUnauthorizedUser() throws UnauthorizedUserException {
        IUserDao ud = new UserDao();
        User testUser = ud.getUserById(1);
        rs.updateRequest(testUser, 9, Status.DENIED);
    }

}
