import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { IUser } from "../../Interfaces/IUser";
import { AppDispatch, RootState } from "../../Store";

export const Employee:React.FC<IUser> = (user: IUser) => {

    const dispatch: AppDispatch = useDispatch();
    const employeesState = useSelector((state:RootState) => state.employees);

    return (
        <tr>
            <td>{user.userId}</td>
            <td>{user.username}</td>
            <td>{user.fName}</td>
            <td>{user.lName}</td>
            <td>{user.email}</td>
            <td>{user.role}</td>
        </tr>
    )
}