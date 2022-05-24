package com.revature.service;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.IUserDao;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReimbursementServiceTest {

    @Mock
    public static IReimbursementDao rd;

    @InjectMocks
    public static ReimbursementService rs;

    @Mock
    public static IUserDao ud;

    @InjectMocks
    public static UserService us;

    @Before
    public void setupBeforeMethods() {
        MockitoAnnotations.openMocks(this);
    }

    User u1 = new User(1, "testUser1", "password", "Test", "User", "tuser1@mail.com", Role.EMPLOYEE);
    User u2 = new User(2, "testUser2", "password", "Test", "User", "tuser2@mail.com", Role.EMPLOYEE);
    User manager = new User(3, "testManager", "password", "Test", "Manager", "tmanager@mail.com", Role.MANAGER);

    Reimbursement r1 = new Reimbursement(1, 65.78, LocalDate.now(), null, "test reimbursement 1",
            u1, null, Status.PENDING, Type.LODGING);

    Reimbursement r2 = new Reimbursement(2, 100.56, LocalDate.now(), null, "test reimbursement 2",
            u2, null, Status.PENDING, Type.LODGING);

    Reimbursement r3 = new Reimbursement(3, 98.36, LocalDate.now(), null, "test reimbursement 3",
            u1, null, Status.APPROVED, Type.TRAVEL);

    Reimbursement r4 = new Reimbursement(4, 22.37, LocalDate.now(), null, "test reimbursement 4",
            u2, null, Status.APPROVED, Type.TRAVEL);

    Reimbursement r5 = new Reimbursement(5, 800.41, LocalDate.now(), null, "test reimbursement 5",
            u1, null, Status.DENIED, Type.FOOD);

    Reimbursement r6 = new Reimbursement(6, 671.58, LocalDate.now(), null, "test reimbursement 6",
            u2, null, Status.DENIED, Type.FOOD);

    Reimbursement r7 = new Reimbursement(7, 900.56, LocalDate.now(), null, "test reimbursement 5",
            u1, null, Status.PENDING, Type.OTHER);

    Reimbursement r8 = new Reimbursement(8, 2364.57, LocalDate.now(), null, "test reimbursement 6",
            u2, null, Status.PENDING, Type.OTHER);

    List<Reimbursement> testList = new ArrayList<>(Arrays.asList(r1, r2, r3, r4, r5, r6, r7, r8));


    // tests for submitRequest ---------------------------------------------------------------------------

    @Test(expected = NegativeAmountException.class)
    public void testSubmitRequestNegativeAmount() throws NegativeAmountException {
        User u = new User();
        rs.submitRequest(-7.89, "negative request", u, Type.LODGING);
    }

    // tests for viewPastTickets -------------------------------------------------------------------------

    @Test
    public void testViewPastTickets() {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewPastTickets("testUser1");
        Assert.assertTrue(list.contains(r3) && list.contains(r5));
    }


    // test for viewPendingTickets -----------------------------------------------------------------------

    @Test
    public void testViewPendingTickets() {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewPendingTickets("testUser1");
        Assert.assertTrue(list.contains(r1) && list.contains(r7));
    }

    // tests for viewAllPending --------------------------------------------------------------------------

    @Test
    public void testViewAllPending() throws UnauthorizedUserException {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewAllPending(manager);
        Assert.assertTrue(list.contains(r1) && list.contains(r2) && list.contains(r7) && list.contains(r8));
    }

    @Test
    public void testViewAllPendings() throws UnauthorizedUserException, IncorrectUsernameOrPasswordException {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewAllPending("manager");
        Assert.assertTrue(list.contains(r1) && list.contains(r2) && list.contains(r7) && list.contains(r8));
    }

    @Test(expected = UnauthorizedUserException.class)
    public void testViewAllPendingUnauthorizedUser() throws UnauthorizedUserException {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewAllPending(u1);
        Assert.assertTrue(list.contains(r1) && list.contains(r2) && list.contains(r7) && list.contains(r8));
    }

    // tests for viewAllResolved --------------------------------------------------------------------------

    @Test
    public void testViewAllResolved() throws UnauthorizedUserException {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewAllResolved(manager);
        Assert.assertTrue(list.contains(r3) && list.contains(r4) && list.contains(r5) && list.contains(r6));
    }

    @Test(expected = UnauthorizedUserException.class)
    public void testViewAllResolvedUnauthorizedUser() throws UnauthorizedUserException {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewAllResolved(u1);
    }

    @Test(expected = UnauthorizedUserException.class)
    public void testViewAllResolvedUnauthorizedUses() throws UnauthorizedUserException {
        User u = new User();
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewAllResolved(u);
    }

    // tests for viewEmployeeRequests --------------------------------------------------------------------

    @Test
    public void testViewEmployeeRequests() throws UnauthorizedUserException {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewEmployeeRequests(manager, u1);
        Assert.assertTrue(list.contains(r1) && list.contains(r3) && list.contains(r5) && list.contains(r7));
    }

    @Test(expected = UnauthorizedUserException.class)
    public void testViewEmployeeRequestsUnauthorizedUser() throws UnauthorizedUserException {
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        List<Reimbursement> list = rs.viewEmployeeRequests(u2, u1);
    }

    // tests for getUserByUsername -----------------------------------------------------------------------


    @Test
    public void testGetUserByUsername() throws IncorrectUsernameOrPasswordException {
        User u = new User();
        u.setUsername("username");
        Mockito.when(us.getUserByUsername(Mockito.any())).thenReturn(u);
        Assert.assertTrue(rs.getUserByUsername("username").equals(u));
    }

    @Test(expected = IncorrectUsernameOrPasswordException.class)
    public void testGetUserByUsernameIncorrectUsername() throws IncorrectUsernameOrPasswordException {
        Mockito.when(us.getUserByUsername(Mockito.anyString())).thenReturn(null);
        rs.getUserByUsername("username");
    }



}
