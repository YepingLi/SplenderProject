import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import { AppEntry } from './app.entry';
import { LoginComponent } from './AppLogin.component';
import { checkToken } from './auth/start.app';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

function login() {
  checkToken(getApp, (err) => {
    console.log(err)
    root.render(
      <React.StrictMode>
        <LoginComponent onSuccessfulLogin={getApp}/>
      </React.StrictMode>
    );
  });
}

function getApp(username: string, rights: string[]) {
  root.render(
    <React.StrictMode>
      <AppEntry username={username} rights={rights}/>
    </React.StrictMode>
  );
}

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
login();
