import { environment } from "../../environment/environment";
import { HttpClient, PostableData } from "../../shared/httpClient";
import { MinimalGameService } from "../models/gameService";
import Session, { ISession, sessionFactory } from "../models/sessions";
import { MD5 } from 'crypto-js';
import { service } from "./service";
import { User } from "../models/user";

export class AdminService{
    private client: HttpClient;
    private baseUrl = environment.lobbyService.getApiUrl();
    constructor() {
        this.client = service.client;
    }
    
    private urlProducer(endpoint: string) {
        return `${this.baseUrl}${endpoint}`
    }
    public getallUsers(){
        return this.client.get<User[]>(this.urlProducer(`${environment.lobbyService.api.users}`));
    }
    public getUser(username: string, aborter?: AbortController) {
        return this.client.get<User>(this.urlProducer(`${environment.lobbyService.api.users}/${username}`), undefined, aborter);
    }

    public adduser(username: string, password: string, colour: string, role: string) {
        return this.client.put<string>(this.urlProducer(`${environment.lobbyService.api.users}/${username}`), {
            
            data: {
                "name": username,
                "password": password,
                "preferredColour": colour,
                "role": role
            }
        })
    }

    public deleteUser(username: string){
        return this.client.delete<string>(this.urlProducer(`${environment.lobbyService.api.users}/${username}`)); 
    }


}