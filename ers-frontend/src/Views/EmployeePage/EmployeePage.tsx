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

    const handleEdit = () => {
        navigator('/edit-user');
    }

    return (
        <div className="container" >
            <NavBarSelector />
            <div className="background">
                <div className="welcome-canvas">
                    <div className='text-box'>
                        <h1>Welcome: {userState.user?.fName} {userState.user?.lName}</h1>
                        <h3 className="card-title-text">Account Information</h3>
                        <h3 className="info-card-text">Email: {userState.user?.email}</h3>
                        <h3 className="info-card-text">Role: {userState.user?.role}</h3>
                        <h3 className="info-card-text">Account ID: {userState.user?.userId}</h3>
                        <h3 className="edit-button" onClick={handleEdit}>Edit</h3>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployeePage