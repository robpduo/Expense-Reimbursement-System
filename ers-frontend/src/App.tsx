import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import {LoginPage} from './Views/LoginPage/LoginPage';
import EmployeePage from './Views/EmployeePage/EmployeePage';
import ExpensePage from './Views/ExpensePage/ExpensePage';
import ViewPending from './Components/ViewPending/ViewPending';
import { ViewAllPending } from './Components/ViewAllPending/ViewAllPending';
<<<<<<< HEAD
=======
import { ViewPastTickets } from './Components/ViewPastTickets/ViewPastTickets';
import { ViewAllResolved } from './Components/ViewAllResolved/ViewAllResolved';
>>>>>>> 71f20d37079038620d9d969bd3b9f62f06044fb8

function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<LoginPage />}/>
          <Route path="/home" element={<EmployeePage />}/>
          <Route path="/create-reimbursement" element={<ExpensePage />} />
          <Route path="/view-pending" element={<ViewPending />} />
          <Route path="/view-all-pending" element={<ViewAllPending/>}/>
<<<<<<< HEAD
=======
          <Route path="/view-past" element={<ViewPastTickets/>}/>
          <Route path="/view-all-resolved" element={<ViewAllResolved/>}/>
>>>>>>> 71f20d37079038620d9d969bd3b9f62f06044fb8
      </Routes>
    </BrowserRouter>
  );
}

export default App;
