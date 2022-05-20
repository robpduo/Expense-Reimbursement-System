import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { IReimbursement } from "../../Interfaces/IReimbursement";
import { viewAllResolved } from "../../Slices/ReimbursementSlice";
import { AppDispatch, RootState } from "../../Store";
import NavBarSelector from "../../Views/NavBarSelector/NavBarSelector";
import Reimbursement from "../../Views/Reimbursement/Reimbursement";
import { ReimbursementTableHeader } from "../../Views/ReimbursementTableHeader/ReimbursementTableHeader";

export const ViewAllResolved:React.FC = () => {

    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state:RootState) => state.user);
    const reimbursementState = useSelector((state:RootState) => state.reimburser);

    useEffect(() => {
        if (userState.user) {
            dispatch(viewAllResolved())
        }
    }, [])

    return(
        <div>
            <NavBarSelector/>
            <div className="view-all-resolved-page">
                <ReimbursementTableHeader/>
                {reimbursementState.reimbursements ? reimbursementState.reimbursements.map((reimbursement: IReimbursement) => {
                        return <Reimbursement {...reimbursement} key={reimbursement.id} />
                    }) :
                        <tr>
                            <td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>
                        </tr>
                    }

            </div>
        </div>
    )
}