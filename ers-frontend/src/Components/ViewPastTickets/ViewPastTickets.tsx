import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { IReimbursement } from "../../Interfaces/IReimbursement";
import { viewAllPending, viewPastTickets } from "../../Slices/ReimbursementSlice";
import { AppDispatch, RootState } from "../../Store";
import NavBarSelector from "../../Views/NavBarSelector/NavBarSelector";
import Reimbursement from "../../Views/Reimbursement/Reimbursement";
import { ReimbursementTableHeader } from "../../Views/ReimbursementTableHeader/ReimbursementTableHeader";

export const ViewPastTickets:React.FC = () => {

    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state:RootState) => state.user);
    const reimbursementState = useSelector((state:RootState) => state.reimburser);

    useEffect(() => {
        if (userState.user) {
            dispatch(viewPastTickets());
        }
    }, [])

    return (
        <div>
            <NavBarSelector/>
            <div className="view-past-tickets-page">
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