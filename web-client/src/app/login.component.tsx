import React, { FormEvent, useState } from 'react';
import { AuthService } from '../auth/authService';

const authService = new AuthService();

/**
 * Login component
 * @param props 
 * @returns 
 */
export function LoginComponent(props: { onSuccessfulLogin: (username: string, rights: string[]) => void }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    function onClick(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        authService.getToken(username, password)
            .then((_) => {
                authService
                    .checkRights()
                    .then((rights) => {
                        props.onSuccessfulLogin(username, rights);
                    }).catch(() => setError("Rights were not able to retrieved. Please contact admin"));
            })
            .catch(() => setError("Username and password combination are not correct. Please retry."));
    }

    if (error !== "") {
        setTimeout(() => setError(""), 5000);
    }

    return (
        <div id="login" style={{height: "100%", display: "flex", alignItems: "center", flexDirection: "column", justifyContent: 'center'}}>
            <div id="login-header">
                <h2>Welcome to Splendor</h2>
                <div>By group Hexanome-03</div>
            </div>
            <form id="login-form" onSubmit={onClick} style={{display: "flex", alignItems: "center", flexDirection: "column"}}>
                <div className="form-floating">
                    <input id="floatingUser" className="form-control" onChange={(e) => setUsername(e.target.value)} placeholder="Username"/>
                    <label htmlFor="floatingUser">Username</label>
                </div>
                <div className="form-floating">
                    <input id="floatingPass" className="form-control" type="password" onChange={(e) => setPassword(e.target.value)} placeholder="Password"/>
                    <label htmlFor="floatingPass">Password</label>
                </div>
                <div className="form-floating">
                    {error !== ""? (<div className="alert alert-danger" role="alert">{error}</div>): (<></>)}
                </div>
                <div className="checkbox mb-3">
                    <button type="submit" className="w-100 btn btn-lg btn-primary">Sign in</button>
                </div>
            </form>
        </div>
    )
}