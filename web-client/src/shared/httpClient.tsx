import { json } from "stream/consumers";

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
    private url: string;
    
    public constructor(url: string) {
        this.url = url;
    }

    /**
     * Build the url and encode the url before returning
     * 
     * @param endpoint The endpoint onn the server we are trying to hit
     * @param data The data to eb added into the URL
     * @returns The encoded URL
     */
    private urlBuilder(endpoint: string, data?: any) {
        let strData = "";
        if (data !== null || data !== undefined) {
            strData = Object.keys(data).map((value) => `${value}=${encodeURIComponent(data[value])}`).join("&");
        }
        let url;
        switch(endpoint.charAt(0)) {
            case "/":
                url = `${this.url}${endpoint}`
                break;
            default:
                url = `${this.url}/${endpoint}`
                
        }
        
        if (strData.length > 0) {
            url = url.concat("?", strData);
        }
        
        return url;
        
    }
    
    private handleCallback(response: Response) {
        if (response.status < 299) {
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

    public get<T>(endpoint: string, {headers, uriData}: PostableData): Promise<T>;
    public get(endpoint: string, {headers, uriData}: PostableData): Promise<any> {
        return fetch(this.urlBuilder(endpoint, uriData), {
            method: "GET",
            headers: headers
        }).then(this.handleCallback);
    }

    public post<T>(endpoint: string, {data, headers, uriData}: PostableData): Promise<T>;
    public post(endpoint: string, {data, headers, uriData}: PostableData) {
        return fetch(this.urlBuilder(endpoint, uriData), {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                ...headers
            },
            body: JSON.stringify(data)
        }).then(this.handleCallback);
    }

    public put<T>(endpoint: string, {data, headers, uriData}: PostableData): Promise<T>;
    public put(endpoint: string, {data, headers, uriData}: PostableData): Promise<any> {
        return fetch(this.urlBuilder(endpoint, uriData), {
            method: "PUT",
            headers: headers,
            body: JSON.stringify(data)
        }).then(this.handleCallback);
    }

    public delete<T>(endpoint: string, {data, headers, uriData}: PostableData): Promise<T>;
    public delete(endpoint: string, {data, headers, uriData}: PostableData): Promise<unknown> {
        return fetch(this.urlBuilder(endpoint, uriData), {
            method: "DELETE",
            headers: headers,
            body: JSON.stringify(data)
        }).then(this.handleCallback);
    }
}