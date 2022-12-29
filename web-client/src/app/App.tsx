import React from 'react';
import { useSelector } from 'react-redux';
import { RouterProvider } from "react-router-dom";
import { authSelector } from './actions/auth.actions';
import { AppRoutes } from './app.router';

function App(props: {username: string, rights: string[]}) {
  let routes = AppRoutes(props.rights);
  return (
      <div className="App">
        <RouterProvider router={routes} />
      </div>
  );
}

export default App;
