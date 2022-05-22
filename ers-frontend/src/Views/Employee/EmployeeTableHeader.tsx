import React from "react";

export const EmployeeTableHeader:React.FC = () => {

    return (
        <tr className="employee-headings">
            <th>ID</th>
            <th>Username</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Role</th>
        </tr>
    )
}