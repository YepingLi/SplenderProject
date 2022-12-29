import { AuthService } from "./AuthService";
import { TokenResponse } from "./TokenResponse";

const authService = new AuthService();

function refresh() {
    return authService.refreshToken().then(() => {
        return authService.checkToken();
    });
}


export function checkToken(successCallback: (username: string, role: string[]) => void, failureCallback: (err: any) => void) {
    authService
        .checkToken(refresh)
        .then((username) => {
            return Promise.all([Promise.resolve(username), authService.checkRights()]);
        })
        .then(([username, role]) => {
            // Renew the token every expiration - 60 seconds
            authService.silentRenewToken();
            successCallback(username, role);
        })
        .catch(failureCallback);
}