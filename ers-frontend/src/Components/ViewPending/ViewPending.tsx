import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { useNavigate } from 'react-router-dom';
import { modifySource, viewPending } from '../../Slices/ReimbursementSlice';
import { EmployeeNavbar } from '../Navbar/EmployeeNavbar';
import { IReimbursement } from '../../Interfaces/IReimbursement';
import Reimbursement from '../../Views/Reimbursement/Reimbursement';
import NavBarSelector from '../../Views/NavBarSelector/NavBarSelector';
import { ReimbursementTableHeader } from '../../Views/Reimbursement/ReimbursementTableHeader';

import "./ViewPending.css";

const ViewPending: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state: RootState) => state.user);
    const reimburseState = useSelector((state: RootState) => state.reimburser);
    const navigator = useNavigate();

    useEffect(() => {
        if (userState.user?.role.toString() === "EMPLOYEE") {
            dispatch(modifySource("view-pending"));
            dispatch(viewPending());
        } else {
            navigator("./")
        }
    }, []);

    return (
        <div>
            <NavBarSelector />
            <div className="reimbursement-page">
                <table>
                    <ReimbursementTableHeader/>
                    {reimburseState.reimbursements ? reimburseState.reimbursements.map((reimburse: IReimbursement) => {
                        return <Reimbursement {...reimburse} key={reimburse.id} />
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

export default ViewPending