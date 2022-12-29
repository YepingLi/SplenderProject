import { createBrowserRouter, Navigate } from "react-router-dom";
import ProtectedRoute from "../shared/ProtectedRoutes";
import { AdminComponent } from "./routes/admin/admin.component";
import { BoardComponent } from "./routes/board/board.component";
import { SessionsComponent } from "./routes/sessions/session.component";

export function AppRoutes(rights: string[]) {
    let isAdmin = rights.filter(x => /admin/.test(x)).length !== 0;
    return createBrowserRouter([
        {
            path: "/board/*",
            element: <BoardComponent />
        },
        {
            path: "/admin",
            element: <ProtectedRoute isAuthenticated={isAdmin} outlet={<AdminComponent />}/>
        },
        {
            path: "/sessions",
            element: <SessionsComponent />
        },
        {
            path: "*",
            element: <Navigate to="/sessions" />
        }
    ])
}