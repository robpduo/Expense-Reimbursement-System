import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { ManagerNavbar } from '../../Components/Navbar/ManagerNavBar';
import { EmployeeNavbar } from '../../Components/Navbar/EmployeeNavbar';
import { RootState } from '../../Store';

const NavBarSelector: React.FC = () => {
    const userState = useSelector((state: RootState) => state.user);

    enum Role {
        MANAGER = 1,
        EMPLOYEE
    }

    if (userState.user?.role.toString() == "MANAGER") {
        return (
            <ManagerNavbar />
        )
    } else {
        return(
            <EmployeeNavbar />
        )
    }


}

export default NavBarSelector