package com.revature.dao;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.utils.ConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao{
    Connection cs = null;
    PreparedStatement stmt = null;

    @Override
    public boolean createUser(User user) {
        try {
            cs = ConnectionSingleton.getConnection();
            String sql = "INSERT INTO users (username, password, first_name, last_name, email, role) VALUES (?, ?, ?, ?, ?, ?);";

            stmt = cs.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getfName());
            stmt.setString(4, user.getlName());
            stmt.setString(5, user.getEmail());

            if (user.getRole().equals(Role.MANAGER) ) {
                stmt.setInt(6, 1);
            } else {
                stmt.setInt(6, 2);
            }

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> readUsers() {
        List<User> users = new ArrayList<>();

        try {

            cs = ConnectionSingleton.getConnection();
            String sql = "SELECT * FROM users;";

            Statement s = cs.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while(rs.next()) {
                User u = new User();

                u.setUserId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setfName(rs.getString(4));
                u.setlName(rs.getString(5));
                u.setEmail(rs.getString(6));

                // TODO
                if(rs.getInt(7) == 1) {
                    u.setRole(Role.MANAGER);
                } else {
                    u.setRole(Role.EMPLOYEE);
                }

                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User getUserById(int id) {
        User u = new User();

        try {
            String sql = "select * from users where user_id = " + id + ";";
            cs = ConnectionSingleton.getConnection();
            Statement s = cs.createStatement();
            s.execute(sql);
            ResultSet rs = s.getResultSet();

            rs.next();
            u.setUserId(rs.getInt(1));
            u.setUsername(rs.getString(2));
            u.setPassword(rs.getString(3));
            u.setfName(rs.getString(4));
            u.setlName(rs.getString(5));
            u.setEmail(rs.getString(6));
            u.setRole(Role.toRole(rs.getInt(7)));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }

    @Override
    public User getUserByUsername(String username) {
        User u = new User();

        try {
            String sql = "select * from users where username = '" + username + "';";
            cs = ConnectionSingleton.getConnection();
            Statement s = cs.createStatement();
            s.execute(sql);
            ResultSet rs = s.getResultSet();

            rs.next();
            u.setUserId(rs.getInt(1));
            u.setUsername(rs.getString(2));
            u.setPassword(rs.getString(3));
            u.setfName(rs.getString(4));
            u.setlName(rs.getString(5));
            u.setEmail(rs.getString(6));
            u.setRole(Role.toRole(rs.getInt(7)));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }


    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET " +
                "username = ?," +
                "first_name = ?," +
                "last_name = ?," +
                "email = ?," +
                "password = ?" +
                "WHERE user_id = " + user.getUserId() + ";";

        try {
            cs = ConnectionSingleton.getConnection();
            PreparedStatement ps = cs.prepareStatement(sql);


            ps.setString(1, user.getUsername());
            ps.setString(2, user.getfName());
            ps.setString(3, user.getlName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }
    @Override
    public boolean updateUser(User user, String column, int nValue) {
        String sql = "UPDATE users SET " + column + " = ? WHERE user_id = " + user.getUserId() +";";

        try {
            cs = ConnectionSingleton.getConnection();
            stmt = cs.prepareStatement(sql);
            stmt.setInt(1, nValue);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public void deleteUser(User user) {

        try {
            cs = ConnectionSingleton.getConnection();
            String sql = "delete from users where user_id=" + user.getUserId() + ";";
            stmt = cs.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
