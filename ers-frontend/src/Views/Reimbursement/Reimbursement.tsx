import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { useNavigate } from 'react-router-dom';
import { viewPending } from '../../Slices/ReimbursementSlice';
import { IReimbursement } from '../../Interfaces/IReimbursement';

const Reimbursement: React.FC<IReimbursement> = (reimburse: IReimbursement) => {
    
    return (
        <tr className="reimbursement-data">
            <td>{reimburse.id}</td>
            <td>${reimburse.amount}</td>
            <td>{reimburse.description}</td>
            <td>{reimburse.author?.username}</td>
            <td>{reimburse.resolver?.username}</td>
            <td>{reimburse.type}</td>
            <td>{reimburse.status}</td>
        </tr>
    )
}

export default Reimbursement