import React from 'react';
import {
    Navigate,
    Outlet,
    RouterProvider,
    createBrowserRouter,
    useParams,
} from 'react-router-dom';

import './app.scss';
import { CustomNavbar } from './navbar.component';
import { BoardComponent } from './routes/board/board.component';
import ProtectedRoute from '../shared/ProtectedRoutes';
import { AdminComponent } from './routes/admin/admin.component';
import { SessionsComponent } from './routes/sessions/session.component';
import { Route } from './models/route';
import { SettingsComponent } from './routes/settings/settings.component';

/**
 * The layout of the application. Functional component
 *
 * @param props The props to be passed to the navbar
 * @returns
 */
const Layout = (props: {
    username: string;
    rights: string[];
    routes: Route[];
    onLogout: () => void;
}) => {
    return (
        <>
            <CustomNavbar {...props} />
            <Outlet />
        </>
    );
};

const AdminWrapper = () => {
    const { sessionId } = useParams();

    if (!sessionId) {
        return <div>Session ID is missing.</div>;
    }

    return <AdminComponent />;
};

/**
 * The main application. Renders the app and the routes, and contains the main Layout
 *
 * @param props
 * @returns
 */
function AppRouter(props: {
    username: string;
    rights: string[];
    onLogout: () => void;
}) {
    let isAdmin =
        props.rights.filter((right) =>
            /.*admin/.test(right.toLowerCase())
        ).length !== 0;

    const routes: Route[] = [
        {
            path: '/admin',
            name: 'Admin',
            element: <ProtectedRoute isAuthenticated={isAdmin} outlet={<AdminComponent />}/>
        },
        {
            path: '/sessions',
            name: 'Sessions',
            element: <SessionsComponent username={props.username} />,
        },
        {
            path: '/settings',
            name: 'Settings',
            element: <SettingsComponent username={props.username} />,
            onRight: true,
        },
        {
            path: '*',
            element: <Navigate to='/sessions' />,
        },
    ];

    let router = createBrowserRouter([
        {
            element: <Layout {...props} routes={routes} />,
            children: routes,
        },
        {
            path: '/board/:id',
            element: <BoardComponent username={props.username} />,
        },
    ]);

    return <RouterProvider router={router} />;
}

export default AppRouter;
