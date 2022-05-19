import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import {LoginPage} from './Views/LoginPage/LoginPage';
import EmployeePage from './Views/EmployeePage/EmployeePage';
import ExpensePage from './Views/ExpensePage/ExpensePage';
import ViewPending from './Components/ViewPending/ViewPending';

function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<LoginPage />}/>
          <Route path="/employee-home" element={<EmployeePage />}/>
          <Route path="/create-reimbursement" element={<ExpensePage />} />
          <Route path="/view-pending" element={<ViewPending />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
