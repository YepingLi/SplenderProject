import React, { FormEvent, useState } from 'react';
import { AuthService } from './auth/AuthService';

const authService = new AuthService();

export function LoginComponent(props: {onSuccessfulLogin: (username: string, rights: string[]) => void}) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    function onClick(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        authService.getToken(username, password).then((token) => {
            authService
            .checkRights()
            .then((rights) => {
                props.onSuccessfulLogin(username, rights);
            }).catch(() => setError("Rights were not able to retrieved. Please contact admin"));
        }).catch(() => setError("Username and password combination are not correct. Please retry."));
    }

    if (error !== "") {
        setTimeout(() => setError(""), 5000);
    }

    return (
        <div>
            <form onSubmit={onClick}>
                <input type={"text"} placeholder="username" onChange={(e) => setUsername(e.currentTarget.value)}/>
                <input type={"password"} placeholder="password" onChange={(e) => setPassword(e.currentTarget.value)}/>
                <button type='submit'>Submit</button>
            </form>
            {error === ""?<></>:<div id={"errorBox"}>{error}</div>}
        </div>
    )
}