import React, { useEffect } from 'react'

import ExpenseForm from '../../Components/ExpenseForm/ExpenseForm';

const ExpensePage = () => {

  useEffect(() => {
    //display all reimbursements when this page loads
  }, []);

  return (
    <div className="new-expense-page">
      //error checking for reimbursements
      <ExpenseForm />
    </div>
  )
}

export default ExpensePage;