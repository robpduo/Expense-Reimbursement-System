import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { useNavigate } from 'react-router-dom';
import { resolveRequest, viewPending } from '../../Slices/ReimbursementSlice';
import { IReimbursement, RType } from '../../Interfaces/IReimbursement';
import { AiOutlineCheck, AiOutlineClose, AiFillDelete } from "react-icons/ai";

const Reimbursement: React.FC<IReimbursement> = (reimburse: IReimbursement) => {    
    let date = new Date();
    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state:RootState) => state.user);
    const reimbursementState = useSelector((state:RootState) => state.reimburser);

    const [id, setId] = useState<number>(0);
    const [status, setStatus] = useState<RType>(RType.OTHER);

    const handleApprove = (event: React.MouseEvent<HTMLElement>,) => {
        let updater = {
            id,
            status,
        };
        // setId(reimburse.id?.valueOf());
        console.log("ID: ", id, "status: ", status);
        dispatch(resolveRequest(updater) );
    }

    const handleDeny = (event: React.MouseEvent<HTMLElement>) => {
        console.log("DENIED: ", reimburse.id);
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
            {/* <td className="icon" onClick={handleApprove}><AiOutlineCheck /></td>
            <td className="icon" onClick={handleDeny}><AiOutlineClose /></td> */}
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
                {/* <td className="icon" onClick={handleDelete}><AiFillDelete /></td> */}
            </tr>
        )
    }
}

export default Reimbursement