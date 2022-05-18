import React from 'react';
import {Link} from 'react-router-dom';
import { useSelector } from 'react-redux';

import defaultImage from '../../deafultpic.jpg';
import './Navbar.css';
import { RootState } from '../../UserStore';

export const EmployeeNavbar: React.FC = () => {

    const handleLogout = () => {

    }

    const user = useSelector((state:RootState) => state.user.user);

    return(
        <nav className="navbar">
            <ul className='nav-menu'>
                <li className="nav-item">
                    <Link to={`/user/${user?.userId}`} className="nav-link">Profile</Link>
                </li>

                <li className="nav-item">
                    <Link to={"/feed"} className="nav-link">Home</Link>
                </li>

                <li className="nav-item">
                    <Link to={"/create-reimbursement"} className="nav-link">Reimbursement Form</Link>
                </li>

                <li className="logout">
                    <Link to={"/login"} className="nav-link">
                        <button className="logout-btn" onClick={handleLogout}>Logout</button>
                    </Link>
                </li>
            </ul>
        </nav>
    )

}
