import React from 'react';
import { appStore } from './app/app.store';
import { Provider } from 'react-redux';
import App from './app/App';

export function AppEntry(props: {username: string, rights: string[]}) {
    return (
        <Provider store={appStore}>
            <App {...props}/>
        </Provider>
    );
}