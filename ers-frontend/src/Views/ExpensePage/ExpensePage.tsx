import React, { useEffect } from 'react'

import ExpenseForm from '../../Components/ExpenseForm/ExpenseForm';
import { EmployeeNavbar } from '../../Components/Navbar/EmployeeNavbar';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

import { RootState } from '../../Store'
import NavBarSelector from '../NavBarSelector/NavBarSelector';

const ExpensePage = () => {
  const userState = useSelector((state:RootState) => state.user);
  const navigator = useNavigate();

  useEffect(()=>{
    if(!userState.user){
        navigator('/');
    }
}, []);

  return (
    <div className="new-expense-page">
      <NavBarSelector />
      <ExpenseForm />
    </div>
  )
}

export default ExpensePage;