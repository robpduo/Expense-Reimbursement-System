import React from "react";

import "./Reimbursement.css";

export const ReimbursementTableHeader:React.FC = () => {

    return (
        <tr className="reimbursement-headings">
            <th>ID</th>
            <th>Amount</th>
            <th>Submitted</th>
            <th>Resolved</th>
            <th>Description</th>
            <th>Author</th>
            <th>Resolver</th>
            <th>Type</th>
            <th>Status</th>
            <th></th>
            <th></th>
        </tr>
    )
}