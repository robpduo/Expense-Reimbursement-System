import React, { useEffect } from 'react'

import ExpenseForm from '../../Components/ExpenseForm/ExpenseForm';
import { EmployeeNavbar } from '../../Components/Navbar/EmployeeNavbar';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

import { RootState } from '../../UserStore'

const ExpensePage = () => {
  const userState = useSelector((state:RootState) => state.user);
  const navigator = useNavigate();

  useEffect(()=>{
    if(!userState.user){
        navigator('/');
    }
    alert("Please Login to Use this function");
}, []);

  return (
    <div className="new-expense-page">
      <EmployeeNavbar />
      <ExpenseForm />
    </div>
  )
}

export default ExpensePage;