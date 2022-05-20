import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { useNavigate } from 'react-router-dom';
import { viewPending } from '../../Slices/ReimbursementSlice';
import { IReimbursement } from '../../Interfaces/IReimbursement';

const Reimbursement: React.FC<IReimbursement> = (reimburse: IReimbursement) => {
    let subDate = new Date(); //Date reimbursement was submitted
    console.log("DATE: ", subDate.toISOString());
    
    return (
        <tr className="reimbursement-data">
            <td>{reimburse.id}</td>
            <td>${reimburse.amount}</td>
            <td>{reimburse.submittedDate}</td>
            <td>{reimburse.description}</td>
            <td>{reimburse.author?.userId}</td>
            <td>{reimburse.type}</td>
        </tr>
    )
}

export default Reimbursement