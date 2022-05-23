import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { modifyToRemove, resolveRequest } from '../../Slices/ReimbursementSlice';
import { IReimbursement } from '../../Interfaces/IReimbursement';
import { AiOutlineCheck, AiOutlineClose, AiFillDelete } from "react-icons/ai";

import "./Reimbursement.css";

const Reimbursement: React.FC<IReimbursement> = (reimburse: IReimbursement) => {

    // let date = new Date();
    let submitted = reimburse.submittedDate;
    let resolved = reimburse.resolvedDate;

    // console.log("Submitted Date: ", reimburse.submittedDate);
    console.log("Resolved Date: ", resolved);

    const dispatch: AppDispatch = useDispatch();
    const reimbursementState = useSelector((state: RootState) => state.reimburser);
    const userState = useSelector((state: RootState) => state.user);

    const handleApprove = () => {
        let updater = {
            id: reimburse.id,
            status: 0
        };

        if (reimburse.id != null) {
            updater.id = reimburse.id;
            dispatch(modifyToRemove(reimburse.id));
        }

        if (userState.user?.username != null) {
            dispatch(resolveRequest(updater));
        }
    }

    const handleDeny = () => {
        let updater = {
            id: reimburse.id,
            status: 1
        };

        if (reimburse.id != null) {
            updater.id = reimburse.id;
            dispatch(modifyToRemove(reimburse.id));
        }

        if (userState.user?.username != null) {
            dispatch(resolveRequest(updater));
        }
    }

    if (userState.user?.role.toString() === "MANAGER" && reimbursementState.source === "view-all") {
        return (
            <tr className="reimbursement-data">
                <td>{reimburse.id}</td>
                <td>${reimburse.amount}</td>

                {reimburse.submittedDate ? <td>{submitted?.toString()}</td> : <td>N/A</td>}
                {reimburse.resolvedDate ? <td>{resolved?.toString()}</td> : <td>N/A</td>}

                <td>{reimburse.description}</td>
                <td>{reimburse.author?.userId}</td>
                <td>{reimburse.resolver?.userId}</td>
                <td>{reimburse.type}</td>
                <td>{reimburse.status}</td>
                <td className="icon" onClick={handleApprove}><AiOutlineCheck /></td>
                <td className="icon" onClick={handleDeny}><AiOutlineClose /></td>
                
            </tr>
        )
    } else {
        return (
            <tr>
                <td>{reimburse.id}</td>
                <td>${reimburse.amount}</td>

                {reimburse.submittedDate ? <td>{submitted?.toString()}</td> : <td>N/A</td>}
                {reimburse.resolvedDate ? <td>{resolved?.toString()}</td> : <td>N/A</td>}

                <td>{reimburse.description}</td>
                <td>{reimburse.author?.userId}</td>
                <td>{reimburse.resolver?.userId}</td>
                <td>{reimburse.type}</td>
                <td>{reimburse.status}</td>
                <td></td>
                <td></td>
            </tr>
        )
    }
}

export default Reimbursement

