import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';

import { submitExpense } from '../../Slices/ReimbursementSlice';
import { RType } from '../../Interfaces/IReimbursement';
import { AppDispatch, RootState } from '../../Store';

import "./ExpenseForm.css";

const ExpenseForm = () => {
    const [prompt, setPrompt] = useState<boolean>(false);
    const [amount, setAmt] = useState<number>(0);
    const [submittedDate, setSubmittedDate] = useState<Date>(new Date());
    const [description, setDesc] = useState<string>("");
    const [type, setType] = useState<number>(0);

    const dispatch: AppDispatch = useDispatch();

    const handleInput = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPrompt(false);
        if (event.target.name === "amount-field") {
            setAmt(event.target.valueAsNumber);

        } else if (event.target.name === "description-field") {
            setDesc(event.target.value);

        } else if (event.target.name === "type-menu") {
            setType(event.target.valueAsNumber);
        }
    }

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        setPrompt(true);
        let expenseForm = {
            amount,
            submittedDate,
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
                <label htmlFor='expense-amount'>Enter Expense Amount <span><input type="number" step="0.01" id='expense-amount' name='amount-field' className="input-field" onChange={handleInput} /></span></label>
                <br />

                <label htmlFor='expense-description'>Description <span><input type="string" id='expense-description' name='description-field' className="input-field" onChange={handleInput} /></span></label>
                <br />

                <label htmlFor='expense-type'>Type of Expense</label>
                <input type="number" id="expense-type" name='type-menu' onChange={handleInput} />
                {/*
                <select id='expense-type' className="type-menu" ref="type-menu">
                    <option value={RType.BLANK}></option>
                    <option value={RType.FOOD}>Food</option>
                    <option value={RType.TRAVEL}>Travel</option>
                    <option value={RType.LODGING}>Lodging</option>
                    <option value={RType.OTHER}>Other</option>
                </select>
                */}
                <br />
                <input type='submit' className="expense-submit-button" value="Submit" />
            </form>
        </div>
    )
}

export default ExpenseForm;