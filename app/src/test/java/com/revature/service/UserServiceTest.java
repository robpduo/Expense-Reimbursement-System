package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.models.User;
import com.revature.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    public static UserDao ud;

    @InjectMocks
    public static UserService us;

    @Before
    public void setupBeforeMethods() {
        MockitoAnnotations.openMocks(this);
    }

    // tests for loginUser -----------------------------------------------------------------------------------------
    User u = new User();

    @Test
    public void testLoginUser() throws IncorrectUsernameOrPasswordException {
        u.setUsername("username");
        u.setPassword("password");
        Mockito.when(ud.getUserByUsername(Mockito.any())).thenReturn(u);
        User testUser = us.loginUser("username", "password");
        Assert.assertNotNull(testUser);
    }

    @Test(expected = IncorrectUsernameOrPasswordException.class)
    public void testLoginUserIncorrectUsername() throws IncorrectUsernameOrPasswordException {
        u.setUsername("username");
        u.setPassword("password");
        Mockito.when(ud.getUserByUsername(Mockito.any())).thenReturn(null);
        User testUser = us.loginUser("uname", "password");
    }

    @Test(expected = IncorrectUsernameOrPasswordException.class)
    public void testLoginUserIncorrectPassword() throws IncorrectUsernameOrPasswordException {
        u.setUsername("username");
        u.setPassword("password");
        Mockito.when(ud.getUserByUsername(Mockito.any())).thenReturn(u);
        User testUser = us.loginUser("uname", "wrongpassword");
    }


}
