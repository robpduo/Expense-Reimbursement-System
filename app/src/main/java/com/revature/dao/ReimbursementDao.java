package com.revature.dao;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.Type;
import com.revature.utils.ConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDao implements IReimbursementDao {

    Connection cs = null;
    PreparedStatement stmt = null;

    @Override
    public boolean createReimbursement(Reimbursement r) {
        String sql = "insert into reimbursement (amount, submitted_date, resolved_date, description, " +
                "reimbursement_author, reimbursement_resolver, reimbursement_status, reimbursement_type) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            cs = ConnectionSingleton.getConnection();
            stmt = cs.prepareStatement(sql);

            stmt.setDouble(1, r.getAmount());
            stmt.setDate(2, Date.valueOf(r.getSumbmittedDate()));
            stmt.setDate(3, Date.valueOf(r.getResolvedDate()));
            stmt.setString(4, r.getDescription());
            stmt.setInt(5, r.getAuthor().getUserId());
            stmt.setInt(6, r.getResolver().getUserId());
            stmt.setInt(7, Status.toInt(r.getStatus()));
            stmt.setInt(8, Type.toInt(r.getType()));

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Reimbursement> readReimbursements() {
        List<Reimbursement> result = new ArrayList<>();

        try{
            cs = ConnectionSingleton.getConnection();
            String sql = "select * from reimbursement;";

            Statement s = cs.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Reimbursement r = new Reimbursement();
                IUserDao ud = new UserDao();

                r.setAmount(rs.getDouble(2));
                r.setSumbmittedDate(rs.getDate(3).toLocalDate());
                r.setResolvedDate(rs.getDate(4).toLocalDate());
                r.setDescription(rs.getString(5));
                r.setAuthor(ud.getUserById(rs.getInt(6)));
                r.setResolver(ud.getUserById(rs.getInt(7)));
                r.setStatus(Status.toStatus(rs.getInt(8)));
                r.setType(Type.toType(rs.getInt(9)));

                result.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean updateReimbursement(int id, String column, String nValue) {
        String sql = "update reimbursement set " + column + " = ? where reimbursement_id = " + id + ";";

        try {
            cs = ConnectionSingleton.getConnection();
            stmt = cs.prepareStatement(sql);
            stmt.setString(1, nValue);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateReimbursement(int id, String column, int nValue) {
        String sql = "update reimbursement set " + column + " = ? where reimbursement_id = " + id + ";";

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
    public void deleteReimbursement(int id) {

        try {
            cs = ConnectionSingleton.getConnection();
            String sql = "delete from reimbursement where reimbursement_id = " + id + ";";
            stmt = cs.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
