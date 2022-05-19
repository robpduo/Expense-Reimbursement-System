import React from 'react';
import {Link} from 'react-router-dom';
import { useSelector } from 'react-redux';

import defaultImage from '../../deafultpic.jpg';
import './Navbar.css';
import { RootState } from '../../Store';

export const EmployeeNavbar: React.FC = () => {

    const handleLogout = () => {

    }

    const user = useSelector((state:RootState) => state.user.user);

    return(
        <nav className="navbar">
            <ul className='nav-menu'>
                <li className="nav-item">
                    <Link to={"/employee-home"} className="nav-link">Home</Link>
                </li>

                <li className="nav-item">
                    <Link to={"/create-reimbursement"} className="nav-link">Reimbursement Form</Link>
                </li>

                <li className="nav-item">
                    <Link to={"/view-pending"} className="nav-link">View Pending</Link>
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
