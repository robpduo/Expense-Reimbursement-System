import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { IReimbursement } from "../../Interfaces/IReimbursement";
import { modifySource, viewAllPending, viewPastTickets } from "../../Slices/ReimbursementSlice";
import { AppDispatch, RootState } from "../../Store";
import NavBarSelector from "../../Views/NavBarSelector/NavBarSelector";
import Reimbursement from "../../Views/Reimbursement/Reimbursement";
import { ReimbursementTableHeader } from "../../Views/Reimbursement/ReimbursementTableHeader";
import FilterForm from "../FilterForm/FilterForm";

import "./ViewPastTickets.css";

export const ViewPastTickets:React.FC = () => {

    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state:RootState) => state.user);
    const reimbursementState = useSelector((state:RootState) => state.reimburser);
    const navigator = useNavigate();

    useEffect(() => {
        if (userState.user) {
            dispatch(modifySource("view-past"));
            dispatch(viewPastTickets());
        } else {
            navigator("./");
        }
    }, [])

    return (
        <div>
            <NavBarSelector/>
            <div className="view-past-tickets-page">
                <FilterForm />
                <table>
                    <ReimbursementTableHeader/>
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

export default ViewPastTickets;