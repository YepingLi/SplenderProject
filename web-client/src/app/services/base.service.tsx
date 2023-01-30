import { HttpClient, PostableData } from "../../shared/httpClient";
import { authConstants, getFromLocal } from "../../auth/constants";

/**
 * Base intercepting service.
 * Handles the wrapping of an http client and manages getting and sending the token for all get/post/put/delete
 */
export class BaseService {
    private client: HttpClient;

    constructor(url: string) {
        this.client = new HttpClient(url);
    }

    private buildPostable(definedPostable: PostableData, postable?: PostableData) {
        if (postable) {
            if (postable.uriData && definedPostable.uriData)
                definedPostable.uriData = {...definedPostable.uriData, ...postable.uriData}
            else if (postable.uriData && !definedPostable.uriData) 
                definedPostable.uriData = postable.uriData
            if (postable.headers && definedPostable.headers)
                definedPostable.uriData = {...definedPostable.uriData, ...postable.uriData}
            else if (postable.headers && !definedPostable.headers)
                definedPostable.headers = postable.headers
            if (postable.data && definedPostable.data)
                definedPostable.data = {...definedPostable.data, ...postable.data}
            else if (postable.data && !definedPostable.data)
                definedPostable.data = postable.data
        }
        return definedPostable;
    }

    /**
     * Handle the HTTP call and add the token to the request
     * @param url The endpoint 
     * @param next The request required
     * @param postable The data that can be injected
     * @returns The promise with the proper type returned
     */
    private httpApiCall(url: string, next: (url: string, data: PostableData) => Promise<any>, postable?: PostableData){
        return new Promise<any>((resolve, reject) => {
            try {
                return resolve(getFromLocal(authConstants.accessToken));
            } catch(err) {
                reject(err);
            }
        }).then((value: string) => {
            let data = this.buildPostable({uriData: {access_token: value}}, postable);
            return next(url, data);
        });
    }

    /**
     * Wraps the http client get
     * 
     * @param url 
     * @param postable 
     */
    protected get<T>(url: string, postable?: PostableData): Promise<T>;
    protected get(url: string, postable?: PostableData): Promise<any> {
        return this.httpApiCall(url, this.client.get.bind(this.client), postable);
    }

    /**
     * Wraps the http client post
     * 
     * @param url 
     * @param postable 
     */
    protected post<T>(url: string, postable?: PostableData): Promise<T>;
    protected post(url: string, postable?: PostableData): Promise<any> {
        return this.httpApiCall(url, this.client.post.bind(this.client), postable);
    }

    /**
     * Wraps the http client post
     * 
     * @param url 
     * @param postable 
     */
    protected put<T>(url: string, postable?: PostableData): Promise<T>;
    protected put(url: string, postable?: PostableData): Promise<any> {
        return this.httpApiCall(url, this.client.put.bind(this.client), postable);
    }

    /**
     * Wraps the http client post
     * 
     * @param url 
     * @param postable 
     */
    protected delete<T>(url: string, postable?: PostableData): Promise<T>;
    protected delete(url: string, postable?: PostableData): Promise<any> {
        return this.httpApiCall(url, this.client.delete.bind(this.client), postable);
    }
}