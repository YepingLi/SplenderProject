import { HttpObserver } from "./observer";

export type PostableData = {
    data?: Record<string, any>,
    headers?: HeadersInit,
    uriData?: Record<string, string>
}

export class ResponseError extends Error {
    resp: Response

    constructor (response: Response) {
        super(response.statusText);
        this.resp = response;
    }
}


export class HttpClient {
    private observers: HttpObserver[];
    constructor(observers?: HttpObserver[]) {
        this.observers = observers !== undefined ? observers : [];
    }

    private handleHttpCall(url: string, next: (url: string, data?: PostableData) => Promise<any>, postable?: PostableData) {
        return this.observers.reduce((finalPromise: Promise<PostableData | undefined>, observer) => {
            return finalPromise.then((data) => observer.observe(data));
        }, Promise.resolve(postable)).then((p) => next(url, p));
    }

    /**
     * Build the url and encode the url before returning
     * 
     * @param endpoint The endpoint onn the server we are trying to hit
     * @param data The data to eb added into the URL
     * @returns The encoded URL
     */
    private urlBuilder(url: string, data?: any) {
        let strData = "";
        if (data !== null || data !== undefined) {
            strData = Object.keys(data).map((value) => `${value}=${encodeURIComponent(data[value])}`).join("&");
        }
        
        if (strData.length > 0) {
            url = url.concat("?", strData);
        }
        return url;
    }
    
    /**
     * Handles the response and deserialization.
     * 
     * @param response 
     * @returns 
     */
    private handleCallback(response: Response) {
        if (response.status < 399) {
            let contentType = response.headers.get("Content-Type");
            if (contentType && /^text\/plain.*/.test(contentType)) {
                return response.text();
            } else if (contentType && /^application\/json.*/.test(contentType)) {
                return response.text().then((resString) => {
                    // we have to convert the value into a json IF it starts with a ([){, otherwise return as a string
                    if (/^\[.*\]$/.test(resString) || /^\{.*\}$/.test(resString)) {
                        return JSON.parse(resString);
                    }
                    console.log("Returning string!");
                    return resString;
                });
            }
            return Promise.resolve();
        } 
        throw new ResponseError(response);
    }

    private handleGet(endpoint: string, data?: PostableData, abort?: AbortController) {
        let signal = abort !== undefined ? abort.signal: undefined;
        return fetch(this.urlBuilder(endpoint, data?.uriData), {
            method: "GET",
            headers: data?.headers,
            signal: signal
        }).then(this.handleCallback);
    }

    public get<T>(endpoint: string, data?: PostableData, abort?: AbortController): Promise<T>;
    public get(endpoint: string, data?: PostableData, abort?: AbortController): Promise<any> {
        return this.handleHttpCall(endpoint, (url, sendableData) => this.handleGet(url, sendableData, abort), data);
    }
    
    private handlePost(endpoint: string, data?: PostableData) {
        return fetch(this.urlBuilder(endpoint, data?.uriData), {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                ...data?.headers
            },
            body: JSON.stringify(data?.data)
        }).then(this.handleCallback);
    }

    public post<T>(endpoint: string, data?: PostableData): Promise<T>;
    public post(endpoint: string, data?: PostableData) {
        return this.handleHttpCall(endpoint, (url, sendableData) => this.handlePost(url, sendableData), data);
    }

    private handlePut(endpoint: string, data?: PostableData) {
        return fetch(this.urlBuilder(endpoint, data?.uriData), {
            method: "PUT",
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                ...data?.headers
            },
            body: JSON.stringify(data?.data)
        }).then(this.handleCallback);
    }

    public put<T>(endpoint: string, data?: PostableData): Promise<T>;
    public put(endpoint: string, data?: PostableData): Promise<any> {
        return this.handleHttpCall(endpoint, (url, sendableData) => this.handlePut(url, sendableData), data);
    }

    private handleDelete(url: string, data?: PostableData) {
        return fetch(this.urlBuilder(url, data?.uriData), {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                ...data?.headers
            },
            body: JSON.stringify(data?.data)
        }).then(this.handleCallback)
    }

    public delete<T>(endpoint: string, data?: PostableData): Promise<T>;
    public delete(endpoint: string, data?: PostableData): Promise<unknown> {
        return this.handleHttpCall(endpoint, (url, sendableData) => this.handleDelete(url, sendableData), data);
    }
}