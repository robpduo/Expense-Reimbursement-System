import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import {LoginPage} from './Views/LoginPage/LoginPage';
import EmployeePage from './Views/EmployeePage/EmployeePage';
import ExpensePage from './Views/ExpensePage/ExpensePage';
import ViewPending from './Components/ViewPending/ViewPending';
import { ViewAllPending } from './Components/ViewAllPending/ViewAllPending';
import { ViewPastTickets } from './Components/ViewPastTickets/ViewPastTickets';
import { ViewAllResolved } from './Components/ViewAllResolved/ViewAllResolved';

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
      </Routes>
    </BrowserRouter>
  );
}

export default App;
