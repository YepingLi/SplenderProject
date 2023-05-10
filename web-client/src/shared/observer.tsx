import { PostableData } from "./httpClient";

export interface Observer {
    observe(): void;
}


export interface HttpObserver {
    observe(data?: PostableData): Promise<any>;
}