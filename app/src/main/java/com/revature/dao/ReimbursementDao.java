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
        String sql = "insert into reimbursement (amount, submitted_date, description, " +
                "reimbursement_author, reimbursement_status, reimbursement_type) " +
                "values (?, ?, ?, ?, ?, ?);";

        try {
            cs = ConnectionSingleton.getConnection();
            stmt = cs.prepareStatement(sql);

            stmt.setDouble(1, r.getAmount());

            stmt.setDate(2, Date.valueOf(r.getSubmittedDate()));
            stmt.setString(3, r.getDescription());
            stmt.setInt(4, r.getAuthor().getUserId());
            stmt.setInt(5, Status.toInt(r.getStatus()));
            stmt.setInt(6, Type.toInt(r.getType()));

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

                r.setId(rs.getInt(1));
                r.setAmount(rs.getDouble(2));
                r.setSubmittedDate(rs.getDate(3).toLocalDate());
                if (rs.getDate(4)!= null) r.setResolvedDate(rs.getDate(4).toLocalDate());
                r.setDescription(rs.getString(5));
                r.setAuthor(ud.getUserById(rs.getInt(6)));
                if (rs.getObject(7) != null) r.setResolver(ud.getUserById(rs.getInt(7)));
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
    public boolean updateReimbursement(int id, String column, Date date) {
        String sql = "update reimbursement set " + column + " = ? where reimbursement_id = " + id + ";";

        try {
            cs = ConnectionSingleton.getConnection();
            stmt = cs.prepareStatement(sql);
            stmt.setDate(1, date);
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
