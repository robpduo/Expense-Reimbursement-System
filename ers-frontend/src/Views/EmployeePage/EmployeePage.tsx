import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import React, { useEffect } from 'react';

import { EmployeeNavbar } from '../../Components/Navbar/EmployeeNavbar'
import { RootState } from '../../Store'
import { ManagerNavbar } from '../../Components/Navbar/ManagerNavBar';
import { render } from '@testing-library/react';
import NavBarSelector from '../NavBarSelector/NavBarSelector';

import "./EmployeePage.css";

const EmployeePage = () => {
    const userState = useSelector((state: RootState) => state.user);
    const navigator = useNavigate();

    useEffect(() => {
        //check if any user is logged in
        if (!userState.user) {
            navigator('/');
        }
    }, []);

    return (
        <>
            <NavBarSelector />
            <div className="welcome-canvas">
                <h1>Welcome: {userState.user?.fName} {userState.user?.lName}</h1>
                <h3>Account Information</h3>
                <h5>Email: {userState.user?.email}</h5>
                <h5>Role: {userState.user?.role}</h5>
                <h5>Account ID: {userState.user?.userId}</h5>
            </div>
        </>
    )
}

export default EmployeePage