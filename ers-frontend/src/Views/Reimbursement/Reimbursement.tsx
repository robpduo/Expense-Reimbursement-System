import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { useNavigate } from 'react-router-dom';
import { resolveRequest, viewPending } from '../../Slices/ReimbursementSlice';
import { IReimbursement, RType, Status } from '../../Interfaces/IReimbursement';
import { AiOutlineCheck, AiOutlineClose, AiFillDelete } from "react-icons/ai";

const Reimbursement: React.FC<IReimbursement> = (reimburse: IReimbursement) => {    
    let date = new Date();
    const dispatch: AppDispatch = useDispatch();
    const reimbursementState = useSelector((state:RootState) => state.reimburser);
    const userState = useSelector((state:RootState) => state.user);

    const handleApprove = (event: React.MouseEvent<HTMLElement>,) => {
        let updater = {
            id: reimburse.id,
            status: Status.APPROVED
        };

        if (reimburse.id != null) {
            updater.id = reimburse.id;
        } 
        if (userState.user?.username != null) {
            dispatch(resolveRequest(updater));
            window.location.reload();
        }
    }

    const handleDeny = (event: React.MouseEvent<HTMLElement>) => {
        let updater = {
            id: reimburse.id,
            status: Status.DENIED,
        };

        console.log(reimburse.id);

        if (reimburse.id != null) {
            updater.id = reimburse.id;
        } 

        dispatch(resolveRequest(updater));
        
        window.location.reload(); 
    }

    const handleDelete = (event: React.MouseEvent<HTMLElement>) => {
        console.log("DELETE: ", reimburse.id);
    }

    if (userState.user?.role.toString() === "MANAGER") {
    return (
        <tr className="reimbursement-data">
            <td>{reimburse.id}</td>
            <td>${reimburse.amount}</td>
            <td>{date.getDate()}</td>
            <td>{reimburse.description}</td>
            <td>{reimburse.author?.userId}</td>
            <td>{reimburse.type}</td>
            <td className="icon" onClick={handleApprove}><AiOutlineCheck /></td>
            <td className="icon" onClick={handleDeny}><AiOutlineClose /></td>
        </tr>
    )
    } else {
        return (
            <tr className="reimbursement-data">
                <td>{reimburse.id}</td>
                <td>${reimburse.amount}</td>
                <td>{date.getDate()}</td>
                <td>{reimburse.description}</td>
                <td>{reimburse.author?.userId}</td>
                <td>{reimburse.type}</td>
                <td className="icon" onClick={handleDelete}><AiFillDelete /></td>
            </tr>
        )
    }
}

export default Reimbursement

