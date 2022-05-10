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

import java.util.ArrayList;
import java.util.LinkedList;
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

    // tests for submitRequest ---------------------------------------------------------------------------

    @Test(expected = NegativeAmountException.class)
    public void testSubmitRequestNegativeAmount() throws NegativeAmountException {
        User u = new User();
        rs.submitRequest(-5, "hello world", u, Type.LODGING);
    }

    // tests for updateRequest ----------------------------------------------------------------------------

    @Test(expected = UnauthorizedUserException.class)
    public void testUpdateRequestUnauthorizedUser() throws UnauthorizedUserException {
        User u = new User();
        u.setRole(Role.EMPLOYEE);
        rs.updateRequest(u, 9, Status.DENIED);
    }

    // tests for viewAllPending --------------------------------------------------------------------------

    @Test
    public void testViewAllPending() throws UnauthorizedUserException {
        User u = new User();
        u.setRole(Role.MANAGER);
        List<Reimbursement> testList = new ArrayList<>();
        Reimbursement r1 = new Reimbursement();
        r1.setStatus(Status.PENDING);
        testList.add(r1);
        Reimbursement r2 = new Reimbursement();
        r2.setStatus(Status.APPROVED);
        testList.add(r2);
        Reimbursement r3 = new Reimbursement();
        r3.setStatus(Status.DENIED);
        testList.add(r3);
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        Assert.assertEquals(1, rs.viewAllPending(u).size());
    }

    @Test(expected = UnauthorizedUserException.class)
    public void testViewAllPendingUnauthorizedUser() throws UnauthorizedUserException {
        User u = new User();
        u.setRole(Role.EMPLOYEE);
        List<Reimbursement> testList = new ArrayList<>();
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        rs.viewAllPending(u);
    }

    // tests for viewAllResolved --------------------------------------------------------------------------

    @Test
    public void testViewAllResolved() throws UnauthorizedUserException {
        User u = new User();
        u.setRole(Role.MANAGER);
        List<Reimbursement> testList = new ArrayList<>();
        Reimbursement r1 = new Reimbursement();
        r1.setStatus(Status.PENDING);
        testList.add(r1);
        Reimbursement r2 = new Reimbursement();
        r2.setStatus(Status.APPROVED);
        testList.add(r2);
        Reimbursement r3 = new Reimbursement();
        r3.setStatus(Status.DENIED);
        testList.add(r3);
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        Assert.assertEquals(2, rs.viewAllResolved(u).size());
    }

    @Test(expected = UnauthorizedUserException.class)
    public void testViewAllResolvedUnauthorizedUser() throws UnauthorizedUserException {
        User u = new User();
        u.setRole(Role.EMPLOYEE);
        List<Reimbursement> testList = new ArrayList<>();
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        rs.viewAllResolved(u);
    }

    // tests for viewEmployeeRequests --------------------------------------------------------------------

    @Test
    public void testViewEmployeeRequests() throws UnauthorizedUserException {
        User manager = new User();
        manager.setRole(Role.MANAGER);
        User u1 = new User();
        u1.setRole(Role.EMPLOYEE);
        u1.setUserId(1);
        User u2 = new User();
        u2.setRole(Role.EMPLOYEE);
        u2.setUserId(2);

        List<Reimbursement> testList = new ArrayList<>();

        Reimbursement r1 = new Reimbursement();
        r1.setStatus(Status.PENDING);
        r1.setAuthor(u1);
        testList.add(r1);

        Reimbursement r2 = new Reimbursement();
        r2.setStatus(Status.APPROVED);
        r2.setAuthor(u1);
        testList.add(r2);

        Reimbursement r3 = new Reimbursement();
        r3.setStatus(Status.DENIED);
        r3.setAuthor(u2);
        testList.add(r3);

        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        Assert.assertEquals(2, rs.viewEmployeeRequests(manager, u1).size());
    }


    @Test(expected = UnauthorizedUserException.class)
    public void testViewEmployeeRequestsUnauthorizedUser() throws UnauthorizedUserException {
        User manager = new User();
        manager.setRole(Role.EMPLOYEE);
        User emp = new User();
        emp.setRole(Role.EMPLOYEE);
        List<Reimbursement> testList = new ArrayList<>();
        Mockito.when(rd.readReimbursements()).thenReturn(testList);
        rs.viewEmployeeRequests(manager, emp);
    }


}
