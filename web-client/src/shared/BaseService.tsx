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

export class BaseService {
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
        if (data != null || data != undefined) {
            strData = Object.keys(data).map((value) => `${value}=${data[value]}`).join("&");
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

        return encodeURI(url);
        
    }

    private handleCallback<T>(response: Response) {
        if (response.status < 299) {
            let contentType = response.headers.get("Content-Type");
            if (contentType && /^text\/plain.*/.test(contentType)) {
                return response.text() as Promise<T>;
            }
            // Cheating... T must be a string, no better idea though
            return response.json() as Promise<T>;
        }
        throw new ResponseError(response);
    }

    public get<T>(endpoint: string, {headers, uriData}: PostableData) {
        return fetch(this.urlBuilder(endpoint, uriData), {
            method: "GET",
            headers: headers
        }).then(this.handleCallback<T>);
    }

    public post<T>(endpoint: string, {data, headers, uriData}: PostableData) {
        return fetch(this.urlBuilder(endpoint, uriData), {
            method: "POST",
            headers: headers,
            body: JSON.stringify(data)
        }).then(this.handleCallback<T>);
    }

    public put<T>(endpoint: string, {data, headers, uriData}: PostableData) {
        return fetch(this.urlBuilder(endpoint, uriData), {
            method: "POST",
            headers: headers,
            body: JSON.stringify(data)
        }).then(this.handleCallback<T>);
    }

    public delete<T>(endpoint: string, {data, headers, uriData}: PostableData) {
        return fetch(this.urlBuilder(endpoint, uriData), {
            method: "POST",
            headers: headers,
            body: JSON.stringify(data)
        }).then(this.handleCallback<T>);
    }
}