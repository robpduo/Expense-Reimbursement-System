import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { useNavigate } from 'react-router-dom';
import { viewPending } from '../../Slices/ReimbursementSlice';
import { EmployeeNavbar } from '../Navbar/EmployeeNavbar';
import { IReimbursement } from '../../Interfaces/IReimbursement';
import Reimbursement from '../../Views/Reimbursement/Reimbursement';
import NavBarSelector from '../../Views/NavBarSelector/NavBarSelector';
<<<<<<< HEAD
=======
import { ReimbursementTableHeader } from '../../Views/ReimbursementTableHeader/ReimbursementTableHeader';
//rafce
>>>>>>> 71f20d37079038620d9d969bd3b9f62f06044fb8


const ViewPending: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state: RootState) => state.user);
    const reimburseState = useSelector((state: RootState) => state.reimburser);
    const navigator = useNavigate();

    useEffect(() => {
        if (userState.user?.role.toString() === "EMPLOYEE") {
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
<<<<<<< HEAD
                    <tr className="reimbursement-headings">
                        <th>Reimbursement ID</th>
                        <th>Amount</th>
                        <th>Date Submitted</th>
                        <th>Description</th>
                        <th>Author</th>
                        <th>Reimbursement Type</th>
                    </tr>
                        {reimburseState.reimbursements ? reimburseState.reimbursements.map((reimburse: IReimbursement) => {
                                return <Reimbursement {...reimburse} key={reimburse.id} />
                        }) :
                            <tr>
                                <td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>
                            </tr>
                        }
=======
                    <ReimbursementTableHeader/>
                    {reimburseState.reimbursements ? reimburseState.reimbursements.map((reimburse: IReimbursement) => {
                        return <Reimbursement {...reimburse} key={reimburse.id} />
                    }) :
                        <tr>
                            <td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>
                        </tr>
                    }
>>>>>>> 71f20d37079038620d9d969bd3b9f62f06044fb8
                </table>
            </div>
        </div>
    )
}

export default ViewPending