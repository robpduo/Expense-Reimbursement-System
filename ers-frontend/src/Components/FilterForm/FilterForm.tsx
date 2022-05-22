import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { RType, Status } from '../../Interfaces/IReimbursement';
import { modAuthorId, modFilterId, modFilterType } from '../../Slices/ReimbursementSlice';
import { AppDispatch, RootState } from '../../Store';
import { ViewAllPending } from '../ViewAllPending/ViewAllPending';

const FilterForm: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();

    const [selType, setSelType] = useState<RType>(RType.BLANK);
    const [idInput, setIdInput] = useState<number>(0);
    const [authorInput, setAuthorInput] = useState<number>(0);

    const reimbursementState = useSelector((state:RootState) => state.reimburser);

    const handleChange = (event: any) => {
        event.preventDefault();

        if (event.target.className === "type-menu") {
            setSelType(event.target.valueAsNumber);
        } else if (event.target.className === "filter-by-id") {
            setIdInput(event.target.valueAsNumber);
        } else if (event.target.className === "filter-by-author") {
            setAuthorInput(event.target.valueAsNumber);
        }
    }

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (selType != RType.BLANK) {
            console.log("X1");
            dispatch(modFilterType(selType));
        }

        if (idInput != 0) {
            console.log("X2");
            dispatch(modFilterId(idInput));
        }

        if (authorInput != 0) {
            console.log("X3");
            dispatch(modAuthorId(authorInput));
        }
    }

    if (reimbursementState.source === "view-all") {
    return (
        <form className="filter-form" onSubmit={handleSubmit}>
            <h3>Filter by Reimbursement Properties!</h3>
            <h5 className="filter-id">
                ID:
                <input type="number" step="0" className="filter-by-id" min="1" onChange={handleChange} />
            </h5>
            <h5 className="filter-author">
                Author:
                <input type="number" step="0" className="filter-by-author" min="1" onChange={handleChange} />
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
            <input type='submit' />
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
                <input type='submit' />
                <br /><br /><br />
            </form>
        )
    }
}

export default FilterForm