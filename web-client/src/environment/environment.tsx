import PasswordInput from "../app/routes/settings/password/password-input.component"

export const environment = {
    lobbyService: {
        url: process.env.REACT_APP_LS_URL,
        auth: {
            url: "/oauth",
            token: "/token",
            username: "/username",
            deleteToken: "/active",
            role: "/role",
            authorization: "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc="
        },
        getAuthUrl: () => {
            return environment.lobbyService.url + environment.lobbyService.auth.url
        },
        getApiUrl: () => {
            return environment.lobbyService.url + environment.lobbyService.api.url
        },
        api: {
            url: "/api",
            sessions: "/sessions",
            gameServices: "/gameservices",
            users: "/users" //change by yeping to do setting part
        },
    },
    gameAssets: "/assets"
}