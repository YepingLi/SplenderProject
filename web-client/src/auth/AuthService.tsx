import { environment } from "../environment/environment";
import { BaseService } from "../shared/BaseService";
import { authConstants, getFromLocal, getNumberFromLocal, saveToLocal } from "./constants";
import { TokenResponse } from "./TokenResponse";


export class AuthService extends BaseService {
    public constructor() {
        super(environment.lobbyService.getAuthUrl())
    }
    
    private saveDataToStorage(response: TokenResponse) {
        saveToLocal(authConstants.accessToken, response.access_token);
        saveToLocal(authConstants.startTime, new Date().getTime() + 2000);
        saveToLocal(authConstants.duration, response.expires_in);
    }
    
    public getToken(username: string, password: string) {
        return this.post<TokenResponse>(environment.lobbyService.auth.token, {
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

        console.log("Refreshing token");

        return this.post<TokenResponse>(environment.lobbyService.auth.token, {
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
            localStorage.removeItem(authConstants.accessToken);
            localStorage.removeItem(authConstants.refreshToken);
            localStorage.removeItem(authConstants.duration);
            localStorage.removeItem(authConstants.startTime);
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
        return this.get<string>(environment.lobbyService.auth.username, {
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

        return this.get<string[]>(environment.lobbyService.auth.role, {
            uriData: {
                access_token: token
            }
        });
    }

    /**
     * Handle refreshing the the token
     * 
     * @returns promise with the token response
     */
    private async refresher() {
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
            console.log("time to refresh: ", secondsToExp, tokenTime, duration, now, (tokenTime + duration));
            this.refresher();
        }
        
        // Start the countdown
        let refreshIn = (secondsToExp - 60) * 1000;
        setTimeout(this.refresher.bind(this), refreshIn);
    }
}