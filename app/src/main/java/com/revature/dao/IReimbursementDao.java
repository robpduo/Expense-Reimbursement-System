package com.revature.dao;

import com.revature.models.Reimbursement;

import java.util.List;

public interface IReimbursementDao {

    public boolean createReimbursement(Reimbursement r);

    public List<Reimbursement> readReimbursements();

    public boolean updateReimbursement(int id, String column, String nValue);

    public boolean updateReimbursement(int id, String column, int nValue);

    public void deleteReimbursement(int id);

}
