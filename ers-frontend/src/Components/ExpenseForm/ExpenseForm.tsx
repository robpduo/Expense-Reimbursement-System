import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';

import { submitExpense } from '../../Slices/ReimbursementSlice';
import { RType } from '../../Interfaces/IReimbursement';
import { AppDispatch, RootState } from '../../Store';

import "./ExpenseForm.css";

const ExpenseForm = () => {
    const [prompt, setPrompt] = useState<boolean>(false);
    const [amount, setAmt] = useState<number>(0);
    const [description, setDesc] = useState<string>("");
    const [type, setType] = useState<number>(4);

    const dispatch: AppDispatch = useDispatch();

    const handleInput = (event: React.ChangeEvent<HTMLInputElement> | any) => {
        setPrompt(false);
        console.log("TYPE: ", event.target.className);
        if (event.target.name === "amount-field") {
            setAmt(event.target.valueAsNumber);

        } else if (event.target.name === "description-field") {
            setDesc(event.target.value);

        } else if (event.target.className === "type-menu") {
            console.log(event.target.value)
            setType(event.target.value);
        }
    }

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        setPrompt(true);
        let expenseForm = {
            amount,
            description,
            type
        }

        dispatch(submitExpense(expenseForm));

    }

    return (
        <div className="new-expense-form">
            {prompt == false ? <></> : <h3 className="prompter">Expense Request Submitted!</h3>}
            <form className="reimbursement-form" onSubmit={handleSubmit}>
                <h3 className='title'>Enter Expense Info</h3>
                <label htmlFor='expense-amount'>Enter Expense Amount <span><input type="number" step="0.01" id='expense-amount' name='amount-field' className="amount-field" min="0.01" onChange={handleInput} /></span></label>
                <br />

                <label htmlFor='expense-description'>Description <span><input type="string" id='expense-description' name='description-field' className="description-field" onChange={handleInput} /></span></label>
                <br />

                <label className="type-menu">Type:
                    <select className="type-menu" onChange={handleInput}>
                        <option value={0}></option>
                        <option value={1}>Lodging</option>
                        <option value={2}>Travel</option>
                        <option value={3}>Food</option>
                        <option value={4}>Other</option>
                    </select>
                </label>

                <br />
                <input type='submit' className="expense-submit-button" value="Submit" />
            </form>
        </div>
    )
}

export default ExpenseForm;