import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import defaultImage from '../../deafultpic.jpg';
import './Navbar.css';
import { AppDispatch, RootState } from '../../Store';
import { logoutUser } from '../../Slices/UserSlice';

export const ManagerNavbar: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state: RootState) => state.user);
    const navigator = useNavigate();

    const handleLogout = () => {
        dispatch(logoutUser());
    }

    useEffect(() => {
        if (!userState.user) {
            navigator("./")
        } 
    }, []);

    return (
        <nav className="navbar">
            <ul className='nav-menu'>
                <li className="nav-item">
                    <Link to={"/home"} className="nav-link">Home</Link>
                </li>

                <li className="nav-item">
                    <Link to={"/create-reimbursement"} className="nav-link">Reimbursement Form</Link>
                </li>

                <li className="nav-item">
                    <Link to={"/view-all-pending"} className="nav-link">View All Pending</Link>
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
