import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';

import { submitExpense } from '../../Slices/ReimbursementSlice';
import { RType } from '../../Interfaces/IReimbursement';
import { AppDispatch, RootState } from '../../UserStore';

const ExpenseForm = () => {
    const [amount, setAmt] = useState<number>(0);
    const [description, setDesc] = useState<string>("");
    const [type, setType] = useState<number>(0);

    const dispatch: AppDispatch = useDispatch();

    const handleInput = (event: React.ChangeEvent<HTMLInputElement>) => {
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
        let expenseForm = {
            amount, 
            description,
            type
        }
        console.log("x1:", expenseForm.amount);
        console.log("x2:", expenseForm.description);
        console.log("x3:", expenseForm.type);
        dispatch(submitExpense(expenseForm));

    }

    return (
        <div className="new-expense-form">
            <form onSubmit={handleSubmit}>
                <label htmlFor='expense-amount'>Enter Expense Amount</label>
                <input type="number" id='expense-amount' name='amount-field' onChange={handleInput}/>
                <br />

                <label htmlFor='expense-description'>Description</label>
                <input type="string" id='expense-description' name='description-field' onChange={handleInput}/>
                <br />

                <label htmlFor='expense-type'>Type of Expense</label>
                <input type="number" id="expense-type" name='type-menu' onChange={handleInput} />
                {/* <select id='expense-type' className="type-menu" ref="type-menu">
                    <option value="FOOD">Food</option>
                    <option value="TRAVEL">Travel</option>
                    <option value="LODGING">Lodging</option>
                    <option value="OTHER">Other</option>
                </select> */}

                <br />
                <input type='submit' className="expense-submit-button" value="Submit" />
            </form>
        </div>
    )
}

export default ExpenseForm;