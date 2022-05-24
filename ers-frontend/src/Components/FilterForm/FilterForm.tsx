import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { RType, Status } from '../../Interfaces/IReimbursement';
import { modAuthorId, modFilterId, modFilterType, viewAllPending, viewAllResolved, viewPastTickets } from '../../Slices/ReimbursementSlice';
import { AppDispatch, RootState } from '../../Store';
import Reimbursement from '../../Views/Reimbursement/Reimbursement';
import { ViewAllPending } from '../ViewAllPending/ViewAllPending';

const FilterForm: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();

    const [selType, setSelType] = useState<number>(0);
    const [idInput, setIdInput] = useState<number>(0);
    const [authorInput, setAuthorInput] = useState<number>(0);

    const reimbursementState = useSelector((state: RootState) => state.reimburser);

    const handleChange = (event: any) => {
        event.preventDefault();

        if (event.target.className === "type-menu") {
            setSelType(event.target.value);
        } else if (event.target.className === "filter-by-id") {
            setIdInput(event.target.valueAsNumber);
        } else if (event.target.className === "filter-by-author") {
            setAuthorInput(event.target.valueAsNumber);
        }
    }

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        if (selType != 0 && isNaN(selType) == false) {
            console.log("x1");
            dispatch(modFilterType(selType));
        } 

        if (idInput != 0 && isNaN(idInput) == false) {
            console.log("x2");
            dispatch(modFilterId(idInput));
        }

        if (authorInput != 0 && isNaN(authorInput) == false) {
            console.log("x3");
            dispatch(modAuthorId(authorInput));
        }
        
    }

    const handleReset = (event: React.MouseEvent<HTMLButtonElement>) => {
        console.log(reimbursementState.source);
        event.preventDefault();
        if (reimbursementState.source === "view-all") {
            dispatch(viewAllPending());
        } else if (reimbursementState.source === "view-resolved") {
            dispatch(viewAllResolved());
        } else if (reimbursementState.source === "view-past") {
            dispatch(viewPastTickets());
        }
    }

    if (reimbursementState.source === "view-all" || reimbursementState.source === "view-resolved") {
        return (
            <form className="filter-form" onSubmit={handleSubmit}>
                <h3>Filter by Reimbursement Properties!</h3>
                <h5 className="filter-id">
                    ID:
                    <input type="number" step="0" className="filter-by-id" min="0" onChange={handleChange} />
                </h5>
                <h5 className="filter-author">
                    Author:
                    <input type="number" step="0" className="filter-by-author" min="0" onChange={handleChange} />
                </h5>
                <h5 className="filter-type">Type:
                    <select className="type-menu" onChange={handleChange}>
                        <option value={0}></option>
                        <option value={3}>Food</option>
                        <option value={2}>Travel</option>
                        <option value={1}>Lodging</option>
                        <option value={4}>Other</option>
                    </select>
                </h5>
                <div className='buttons'><input type='submit' /> <button className='reset' onClick={handleReset}>Reset</button></div>
                <br /><br /><br />
            </form>
        )
    } else {
        return (
            <form className="filter-form" onSubmit={handleSubmit}>
                <h3>Filter by Reimbursement Properties!</h3>
                <h5 className="filter-id">
                    ID:
                    <input type="number" step="0" className="filter-by-id" min="1" onChange={handleChange} />
                </h5>
                <h5 className="filter-type">Type:
                    <select className="type-menu" onChange={handleChange}>
                        <option value={RType.BLANK}></option>
                        <option value={RType.FOOD}>Food</option>
                        <option value={RType.TRAVEL}>Travel</option>
                        <option value={RType.LODGING}>Lodging</option>
                        <option value={RType.OTHER}>Other</option>
                    </select>
                </h5>
                <div className='buttons'><input type='submit' /> <button className='reset' onClick={handleReset}>Reset</button></div>
                <br /><br /><br />
            </form>
        )
    }
}

export default FilterForm