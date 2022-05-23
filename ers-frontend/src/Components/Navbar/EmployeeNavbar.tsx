import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import './Navbar.css';
import { AppDispatch, RootState } from '../../Store';
import { logoutUser } from '../../Slices/UserSlice';

export const EmployeeNavbar: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const user = useSelector((state:RootState) => state.user.user);
    const navigator = useNavigate();

    const handleLogout = () => {
        console.log("LOG ME OUT");
        dispatch(logoutUser());
    }

    return(
        <nav className="navbar">
            <img src="revature-logo.webp" id="logo"></img>
            <ul className='nav-menu'>
                <li className="nav-item">
                    <Link to={"/home"} className="nav-link">Home</Link>
                </li>

                <li className="nav-item">
                    <Link to={"/create-reimbursement"} className="nav-link">Reimbursement Form</Link>
                </li>

                <li className="nav-item">
                    <Link to={"/view-pending"} className="nav-link">View Pending</Link>
                </li>
                <li className="nav-item">
                    <Link to={"/view-past"} className="nav-link">View Past</Link>
                </li>
                <li className="logout">
                    <Link to={"/"} className="nav-link">
                        <button className="logout-btn" onClick={handleLogout}>Logout</button>
                    </Link>
                </li>
            </ul>
        </nav>
    )

}
