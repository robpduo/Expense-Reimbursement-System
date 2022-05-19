import React from 'react';
import ReactDOM from 'react-dom/client';

import {Provider} from 'react-redux';
import {userStore} from './Store';

import './index.css';
import App from './App';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <Provider store={userStore}>
    <App />
  </Provider>
);