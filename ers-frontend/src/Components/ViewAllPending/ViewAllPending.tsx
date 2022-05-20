import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { IReimbursement } from "../../Interfaces/IReimbursement";
import { viewAllPending } from "../../Slices/ReimbursementSlice";
import { AppDispatch, RootState } from "../../Store";
import NavBarSelector from "../../Views/NavBarSelector/NavBarSelector";
import Reimbursement from "../../Views/Reimbursement/Reimbursement";

export const ViewAllPending:React.FC = () => {

    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state:RootState) => state.user);
    const reimbursementState = useSelector((state:RootState) => state.reimburser);

    useEffect(() => {
        if (userState.user) {
            dispatch(viewAllPending());
        }
    })

    return(
        <div>
            <NavBarSelector/>
            <div className="view-all-pending-page">
                <table>
                    <tr className="reimbursement-headings">
                        <th>Reimbursement ID</th>
                        <th>Amount</th>
                        <th>Date Submitted</th>
                        <th>Description</th>
                        <th>Author</th>
                        <th>Reimbursement Type</th>
                    </tr>
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