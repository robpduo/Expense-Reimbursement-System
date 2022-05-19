import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../Store'; //change userstore to store
import { useNavigate } from 'react-router-dom';
import { viewPending } from '../../Slices/ReimbursementSlice';
import { EmployeeNavbar } from '../Navbar/EmployeeNavbar';
import { IReimbursement } from '../../Interfaces/IReimbursement';
import Reimbursement from '../../Views/Reimbursement/Reimbursement';
//rafce


const ViewPending: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state: RootState) => state.user);
    const reimburseState = useSelector((state: RootState) => state.reimburser);
    const navigator = useNavigate();

    useEffect(() => {
        if (userState.user) {
            dispatch(viewPending());
        }
        console.log("P: ", reimburseState.reimbursements?.at(1)?.reimbursement_id);
    }, []);

    return (
        <div>
            <EmployeeNavbar />
            <div className="reimbursement-page">
                {reimburseState.reimbursements ? reimburseState.reimbursements.map((reimburse: IReimbursement) => {
                    return <Reimbursement {...reimburse} key={ reimburse.reimbursement_id} />
                }) :
                    <p>Here1</p>
                }
            </div>
        </div>
    )
}

export default ViewPending