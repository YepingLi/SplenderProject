import { getFromLocal, authConstants } from "../../auth/constants";
import { PostableData } from "../httpClient";
import { HttpObserver } from "../observer";

export class TokenObserverInjector implements HttpObserver {
    private buildPostable(definedPostable: Record<string, any>, postable?: Record<string, any>) {
        if (postable) {
            Object.keys(postable).forEach(key => {
                let definedValue = definedPostable[key];
                if (definedValue === undefined) {
                    definedPostable[key] = postable[key];
                } else {
                    definedPostable[key] = {...definedValue, ...postable[key]}
                }
            });
        }
        return definedPostable;
    }

    /**
     * Handle the HTTP call and add the token to the request
     * 
     * @param url The endpoint 
     * @param next The request required
     * @param postable The data that can be injected
     * @returns The promise with the proper type returned
     */
    observe(postable?: PostableData) {
        return new Promise<any>((resolve, reject) => {
            try {
                return resolve(getFromLocal(authConstants.accessToken));
            } catch(err) {
                reject(err);
            }
        }).then((value: string) => {
            return this.buildPostable({uriData: {access_token: value}}, postable);
        });
    }
}