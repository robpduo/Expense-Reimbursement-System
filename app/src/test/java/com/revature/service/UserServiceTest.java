package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

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

    // tests for registerUser ---------------------------------------------------------------------------------

    /*
    @Test
    public void testRegisterUser() throws ExistingUserException {
        User u = new User();
        u.setUsername("username");
        Mockito.when(ud.getUserByUsername(Mockito.any())).thenReturn(null);
        us.registerUser(u);
    }

     */

    @Test(expected = ExistingUserException.class)
    public void testRegisterUserExistingUser() throws ExistingUserException {
        User u = new User();
        Mockito.when(ud.getUserByUsername(Mockito.anyString())).thenReturn(u);
        us.registerUser(u);
    }

    // tests for viewAllEmployees -----------------------------------------------------------------------------
    @Test
    public void testViewAllEmployees() throws UnauthorizedUserException {
        User manager = new User();
        manager.setRole(Role.MANAGER);

        User u1 = new User();
        u1.setRole(Role.EMPLOYEE);

        User u2 = new User();
        u2.setRole(Role.EMPLOYEE);

        User u3 = new User();
        u3.setRole(Role.EMPLOYEE);

        List<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(manager);

        Mockito.when(ud.readUsers()).thenReturn(users);
        Assert.assertEquals(3, us.viewAllEmployees(manager).size());
    }

    @Test(expected = UnauthorizedUserException.class)
    public void testViewAllEmployeesUnauthorizedUser() throws UnauthorizedUserException {
        User u = new User();
        u.setRole(Role.EMPLOYEE);
        List<User> users = new ArrayList<>();
        Mockito.when(ud.readUsers()).thenReturn(users);
        us.viewAllEmployees(u);
    }

}
