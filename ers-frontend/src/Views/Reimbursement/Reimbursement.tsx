import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { useNavigate } from 'react-router-dom';
import { viewPending } from '../../Slices/ReimbursementSlice';
import { IReimbursement } from '../../Interfaces/IReimbursement';

const Reimbursement: React.FC<IReimbursement> = (reimburse: IReimbursement) => {
    return (
        <div className="Reimbursements">
            <div className="reimbursements">
                <h3 className="single-reimbursement">{reimburse.reimbursement_id} {reimburse.description}</h3>
            </div>
        </div>
    )
}

export default Reimbursement