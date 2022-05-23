import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { modifyToRemove, resolveRequest } from '../../Slices/ReimbursementSlice';
import { IReimbursement, RType } from '../../Interfaces/IReimbursement';
import { AiOutlineCheck, AiOutlineClose, AiFillDelete } from "react-icons/ai";
import { Role } from '../../Interfaces/IUser';

const Reimbursement: React.FC<IReimbursement> = (reimburse: IReimbursement) => {
    let date = new Date();
    const dispatch: AppDispatch = useDispatch();
    const reimbursementState = useSelector((state: RootState) => state.reimburser);
    const userState = useSelector((state: RootState) => state.user);

    const handleApprove = (event: React.MouseEvent<HTMLElement>) => {
        console.log(event);
        event.preventDefault();
        let updater = {
            id: reimburse.id,
            status: 0
        };

        if (reimburse.id != null) {
            updater.id = reimburse.id;
            dispatch(modifyToRemove(reimburse.id));
            dispatch(resolveRequest(updater));
        }

        // if (userState.user?.username != null) {
        //     dispatch(resolveRequest(updater));
        // }

    }

    const handleDeny = (event: React.MouseEvent<HTMLElement>) => {
        console.log(event);
        event.preventDefault();
        let updater = {
            id: reimburse.id,
            status: 1
        };

        if (reimburse.id != null) {
            updater.id = reimburse.id;
            dispatch(modifyToRemove(reimburse.id));
            dispatch(resolveRequest(updater));
        }

        // if (userState.user?.username != null) {
        //     dispatch(resolveRequest(updater));
        // }
    }

    if (userState.user?.role.toString() === "MANAGER" && reimbursementState.source === "view-all") {
        return (
            <tr className="reimbursement-data">
                <td>{reimburse.id}</td>
                <td>${reimburse.amount}</td>
                <td>DD: {date.getDate()} Month {date.getMonth()}</td>
                <td>DD: {date.getDate()} Month {date.getMonth()}</td>
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
                <td>D: {date.getDate()} MM {date.getMonth()}</td>
                <td>D: {date.getDate()} MM {date.getMonth()}</td>
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

