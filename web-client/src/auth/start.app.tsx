import { AuthService } from "./authService";

const authService = new AuthService();
/**
 * Refresh the token and try getting the username again
 * 
 * @returns 
 */
async function refresh() {
    return authService.refreshToken().then(() => {
        return authService.checkToken();
    });
}

/**
 * Gets the token, username and rights/roles
 * 
 * @param successCallback On successful retrieval
 * @param failureCallback On failure to retrieve, we handle with this function
 */
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

export function stopSilentRenew() {
    authService.removeNextRenew();
}