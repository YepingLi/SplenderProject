import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import App from './app/App';
import { Provider } from 'react-redux';
import { appStore } from './app/app.store';
import LoaderComponent from './sharedComponents/loader.component';
import { checkToken } from './auth/start.app';
import { LoginComponent } from './app/login.component';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);


root.render(
  <React.StrictMode>
    <LoaderComponent />
  </React.StrictMode>
);

function renderApp(username: string, rights: string[]){
  root.render(
    <React.StrictMode>
      <Provider store={appStore} >
        <App username={username} rights={rights} onLogout={() => window.location.replace("/")}/>
      </Provider>
    </React.StrictMode>
  );
}

checkToken(renderApp, (err) => {
  root.render(
    <React.StrictMode>
      <LoginComponent onSuccessfulLogin={renderApp} />
    </React.StrictMode>
  );
});


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
