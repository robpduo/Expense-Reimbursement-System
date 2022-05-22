import React, { useEffect } from "react";
import { Root } from "react-dom/client";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { IUser } from "../../Interfaces/IUser";
import { getAllEmployees } from "../../Slices/EmployeesSlice";
import { AppDispatch, RootState } from "../../Store";
import { Employee } from "../../Views/Employee/Employee";
import { EmployeeTableHeader } from "../../Views/Employee/EmployeeTableHeader";
import NavBarSelector from "../../Views/NavBarSelector/NavBarSelector";

import "./ViewAllEmployees.css";

export const ViewAllEmployees:React.FC = () => {

    const dispatch: AppDispatch = useDispatch();
    const userState = useSelector((state:RootState) => state.user);
    const employeesState = useSelector((state:RootState) => state.employees);
    const navigator = useNavigate();

    useEffect(() => {
        if (userState.user?.role.toString() === "MANAGER") {
            dispatch(getAllEmployees());
        } else {
            navigator("./");
        }
    }, []);

    return (
        <div>
            <NavBarSelector/>
            <div className="view-all-employees-page">
                <table>
                    <EmployeeTableHeader/>
                    {employeesState.employees ? employeesState.employees.map((employee: IUser) => {
                        return <Employee {...employee} key={employee.userId}/>
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