import { environment } from "../environment/environment";
import { HttpClient } from "../shared/httpClient";
import { authConstants, getFromLocal, getNumberFromLocal, saveToLocal } from "./constants";
import { TokenResponse } from "../app/models/tokenResponse";
import { RoleType } from "./roleType";



export class AuthService {
    private client: HttpClient;
    // Acts as a store -> react can cause this value to try to be initialized more than once.
    private static nextTimeout: NodeJS.Timeout | null = null;
    private url: string = environment.lobbyService.getAuthUrl();
    public constructor() {
        this.client = new HttpClient();
    }
    
    private urlProducer(endpoint: string) {
        return `${this.url}${endpoint}`
    }

    private saveDataToStorage(response: TokenResponse) {
        console.log("invalid token was: ", response.access_token)
        saveToLocal(authConstants.accessToken, response.access_token);
        saveToLocal(authConstants.startTime, new Date().getTime() + 2000);
        saveToLocal(authConstants.duration, response.expires_in);
    }

    private removeAll() {
        localStorage.removeItem(authConstants.accessToken);
        localStorage.removeItem(authConstants.refreshToken);
        localStorage.removeItem(authConstants.duration);
        localStorage.removeItem(authConstants.startTime);
    }
    
    public getToken(username: string, password: string) {
        return this.client.post<TokenResponse>(this.urlProducer(environment.lobbyService.auth.token), {
            headers: {Authorization: environment.lobbyService.auth.authorization}, 
            uriData: {
                "grant_type": "password",
                username: username,
                password: password
            }
        }).then((value) => {
            if (value) {
                saveToLocal(authConstants.refreshToken, value.refresh_token);
                this.saveDataToStorage(value);
            }
            this.silentRenewToken();
            return value;
        });
    }

    public refreshToken(): Promise<TokenResponse> {
        let token;
        try {
            token = getFromLocal(authConstants.refreshToken);
        } catch (error) {
            return Promise.reject("invalid token");
        }

        return this.client.post<TokenResponse>(this.urlProducer(environment.lobbyService.auth.token), {
            headers: {Authorization: environment.lobbyService.auth.authorization}, 
            uriData: {
                "grant_type": "refresh_token",
                refresh_token: token
            }
        }).then((value) => {
            if (value) {
                this.saveDataToStorage(value);
            }
            return value;
        }).catch((err) => {
            this.removeAll();
            throw err;
        });
    }
    

    public checkToken(onFail?: () => Promise<string>): Promise<string> {
        let token;
        try {
            token = getFromLocal(authConstants.accessToken);
        } catch (error) {
            return Promise.reject("invalid token");
        }

        return this.client.get<string>(this.urlProducer(environment.lobbyService.auth.username), {
            uriData: {
                access_token: token
            }
        }).catch((err) => {
            console.error(err);
            if (onFail) {
                return onFail();
            }
            return Promise.reject("token is invalid");
        });
    }

    public checkRights() {
        let token;
        try {
            token = getFromLocal(authConstants.accessToken);
        } catch (error) {
            return Promise.reject("invalid token");
        }

        return this.client.get<RoleType[]>(this.urlProducer(environment.lobbyService.auth.role), {
            uriData: {
                access_token: token
            }
        }).then((roleList) => roleList.map(role => role.authority));
    }

    /**
     * Handle refreshing the the token
     * 
     * @returns promise with the token response
     */
    private async refresher() {
        AuthService.nextTimeout = null;
        return this.refreshToken().then(this.silentRenewToken.bind(this));
    }

    /**
     * Refresh the token silently.
     */
    public silentRenewToken() {
        let tokenTime: number = getNumberFromLocal(authConstants.startTime) / 1000;
        let duration: number = getNumberFromLocal(authConstants.duration);
        let now = new Date().getTime()/1000;

        let secondsToExp = (tokenTime + duration) - now;
        if (secondsToExp < 60){
            this.refresher();
            return;
        }
        
        if (AuthService.nextTimeout !== null) {
            return;
        }

        let refreshIn = (secondsToExp - 60) * 1000;
        AuthService.nextTimeout = setTimeout(this.refresher.bind(this), refreshIn);
    }
    
    /**
     * Perform the logout requested
     * 
     * @returns A failure or the result of the logout 
     */
    public logout(): Promise<string | unknown> {
        let token;
        try {
            token = getFromLocal(authConstants.accessToken);
        } catch (err) {
            return Promise.reject("Failed to retrieve token");
        }

        this.removeNextRenew();

        return this.client.delete(
            this.urlProducer(environment.lobbyService.auth.deleteToken),
            {uriData: {access_token: token}}
        ).then(() => {
            this.removeAll();
        });
    }

    public removeNextRenew() {
        // Remove the token if we logout
        if (AuthService.nextTimeout) {
            clearTimeout(AuthService.nextTimeout);
            AuthService.nextTimeout = null;
        }
    }
}