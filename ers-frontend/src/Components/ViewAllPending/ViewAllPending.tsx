import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { IReimbursement } from "../../Interfaces/IReimbursement";
import { modifySource, viewAllPending } from "../../Slices/ReimbursementSlice";
import { AppDispatch, RootState } from "../../Store";
import NavBarSelector from "../../Views/NavBarSelector/NavBarSelector";
import Reimbursement from "../../Views/Reimbursement/Reimbursement";
import { AiOutlineCheck, AiOutlineClose } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import { ReimbursementTableHeader } from "../../Views/ReimbursementTableHeader/ReimbursementTableHeader";
import ResolverForm from "../ResolverForm/ResolverForm";

export const ViewAllPending:React.FC = () => {

    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state:RootState) => state.user);
    const reimbursementState = useSelector((state:RootState) => state.reimburser);
    const navigator = useNavigate();

    useEffect(() => {
        if (userState.user?.role.toString() === "MANAGER") {
            dispatch(modifySource("view-all"));
            dispatch(viewAllPending());
        } else {
            navigator("./");
        }
    },[]);

    return(
        <div>
            <NavBarSelector/>
            <ResolverForm />
            <div className="view-all-pending-page">
                <table>
                        <ReimbursementTableHeader />
                        {reimbursementState.reimbursements ? reimbursementState.reimbursements.map((reimbursement: IReimbursement) => {
                                return <Reimbursement {...reimbursement} key={reimbursement.id} />
                        }) :
                            <tr>
                                <td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>
                            </tr>
                        }

                </table>
            </div>
        </div>
    )

}