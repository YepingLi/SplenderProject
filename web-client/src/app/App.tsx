import React, { useEffect, useState } from 'react';

import "./app.scss"
import AppRouter from './app.router';
import { Toast, ToastBody, ToastContainer, ToastHeader } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { removeToast, toastSelector } from './actions/toast.actions';

/**
 * Main application: Handles login/token validation
 * @returns The main application view
 */
function App(props: {username: string, rights: string[], onLogout: () => void}) {
  const toasts = useSelector(toastSelector).toast;
  const dispatch= useDispatch();
  return (
    <div id="app" className="app" style={{height: "100vh", width: "100vw",maxHeight: "100vh", maxWidth: "100vw"}}>
      <AppRouter {...props} />
      <ToastContainer position="top-end">
        {toasts.map((toast) => {
          const timeout = setTimeout(() => dispatch(removeToast(toast.id)), 7500);

          return (
            <Toast key={`toast-${toast.id}`} onClose={() => {
              clearTimeout(timeout);
              dispatch(removeToast(toast.id))
            }}>
              <ToastHeader>
                <strong className="me-auto">{toast.source}</strong>
              </ToastHeader>
              <ToastBody>{toast.msg}</ToastBody>
            </Toast>
          )
        })}
      </ToastContainer>
    </div>
  );

}

export default App;
