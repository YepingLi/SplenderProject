import React from "react";
import { Button, Container, Nav, Navbar } from "react-bootstrap";
import { AuthService } from "../auth/authService";
import { matchRoutes, useLocation, useNavigate } from "react-router-dom";
import { CustomMap } from "../shared/map";
import { Route } from "./models/route";

function fixPath(path: string) {
    return path.replace(/\/:.*/, "");
}

function isMulti(path: string ) {
    return /\/:.*/.test(path);
}

const authService = new AuthService();

/**
 * Functional component for the nav bar
 * 
 * @param props: Props required for rendering the nav bar
 * @returns The nav bar
 */
export function CustomNavbar(props: {username: string, rights: string[], routes: Route[], onLogout: () => void}) {
    let navigate = useNavigate();
    function logout() {
        authService.logout().then(() => {
            console.log("Successfully logged out");
            props.onLogout();
        }).catch((err) => {
            console.error("Failed to logout", err);
        });
    }

    let current = useLocation();
    let matched = matchRoutes(props.routes, current);
    if (!matched) {
        console.error("Unknown route");
        matched = [{params: {}, pathname: "unknown", pathnameBase: "unknown", route: {path: "", element: <></>, name: "unknown"}}]
    }

    let currentRoute = matched[0];
    let components = props.routes
        .filter((route) => route.path !== "*")
        .map((route, index) => {
            let isCurrent = route.path === currentRoute.route.path;
            return {element: (
                <Nav.Link active={isCurrent} key={`nav-key-${index}`} disabled={isCurrent || isMulti(route.path)} onClick={() => navigate(fixPath(route.path))}>
                    {route.name}
                </Nav.Link >
            ), isRight: route.onRight !== undefined && route.onRight}
        }).reduce((accumulator, next) => {
            accumulator.putIfAbsent(next.isRight, []).get(next.isRight)?.push(next.element);
            return accumulator;
        }, new CustomMap<boolean, JSX.Element[]>())
    return (
        <Navbar bg="light" id={"navbar"}>
            <Container>
                <Navbar.Toggle />
                <Navbar.Collapse>
                    <Nav variant="pills" className={"me-auto"}>
                        {components.get(false)}
                    </Nav>
                    <Nav variant="pills">
                        {components.get(true)}
                        <Button onClick={logout}>
                            Logout
                        </Button>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}