const PATH_TO_ASSETS = '/gameAssets';
const PATH_TO_CARDS = `${PATH_TO_ASSETS}/Cards`
const PATH_TO_TOKENS = `${PATH_TO_ASSETS}/Tokens`
const PATH_TO_NOBLES = `${PATH_TO_ASSETS}/Nobles`


function getSimplePath(inner: string, name: string) {
    return `${inner}/${name}.png`
}

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
            users: "users" //change by yeping to do setting part
        },
    },
    gameAssets: {
        getCardPath: (name: string, level: number, features?: {isOrient?: boolean, type?: string}) => {
            if (features?.type && features?.isOrient) {
                throw new Error("Cannot have orient with type!");
            }
            let mid = features?.type ? `${features.type}/`:'';
            return `${PATH_TO_CARDS}/Level${level}/${mid}${name}.png`;
        },
        getNoblePath: (name: string) => {
            return getSimplePath(PATH_TO_NOBLES, name);
        },
        getTokenPath: (name: string) => {
            return getSimplePath(PATH_TO_TOKENS, name);
        }
    }
}