export const environment = {
    lobbyService: {
        url: "http://127.0.0.1:4242",
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
        }
    },
    gameService: {
        url: "http://127.0.0.1:8080"
    }
}