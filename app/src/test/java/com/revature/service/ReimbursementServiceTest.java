package com.revature.service;

import com.revature.dao.IReimbursementDao;
import com.revature.dao.IUserDao;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @Test
    public void testViewPastTickets() {
        IUserDao ud = new UserDao();
        List<Reimbursement> testList = rs.viewPastTickets(ud.getUserById(2));
        Assert.assertEquals(2, testList.size());
    }

    //

}
