import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import {LoginPage} from './Views/LoginPage/LoginPage';
import EmployeePage from './Views/EmployeePage/EmployeePage';
import ExpensePage from './Views/ExpensePage/ExpensePage';
import ViewPending from './Components/ViewPending/ViewPending';
import { ViewAllPending } from './Components/ViewAllPending/ViewAllPending';
import { ViewPastTickets } from './Components/ViewPastTickets/ViewPastTickets';
import { ViewAllResolved } from './Components/ViewAllResolved/ViewAllResolved';
import { ViewAllEmployees } from './Components/ViewAllEmployees/ViewAllEmployees';
import EditUserPage from './Views/EditUserPage/EditUserPage';
import RegisterForm from './Components/RegisterForm/RegisterForm';

function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<LoginPage />}/>
          <Route path="/home" element={<EmployeePage />}/>
          <Route path="/create-reimbursement" element={<ExpensePage />} />
          <Route path="/view-pending" element={<ViewPending />} />
          <Route path="/view-all-pending" element={<ViewAllPending/>}/>
          <Route path="/view-past" element={<ViewPastTickets/>}/>
          <Route path="/view-all-resolved" element={<ViewAllResolved/>}/>
          <Route path="/view-all-employees" element={<ViewAllEmployees/>}/>
          <Route path="/edit-user" element={<EditUserPage />}/>
          <Route path="/register-user" element={<RegisterForm />}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
