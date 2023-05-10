import { environment } from "../../environment/environment";
import { HttpClient, PostableData } from "../../shared/httpClient";
import { MinimalGameService } from "../models/gameService";
import Session, { ISession, sessionFactory } from "../models/sessions";
import { MD5 } from 'crypto-js';
import { service } from "./service";
import { User } from "../models/user";

export class LobbyService {
    private client: HttpClient;
    private baseUrl = environment.lobbyService.getApiUrl();

    constructor() {
        this.client = service.client;
    }

    private urlProducer(endpoint: string) {
        return `${this.baseUrl}${endpoint}`
    }
    
    private convertToObjects(sessions: Record<string, ISession>) {
        return Object.keys(sessions).map(key => sessionFactory(sessions[key], key));
    }
    
    public getSessions() {
        return this.client.get<{sessions: Record<string, ISession>}>(this.urlProducer(environment.lobbyService.api.sessions))
            .then(({sessions}) => this.convertToObjects(sessions));
    }

    public getSession(id: string) {
        return this.client.get<ISession>(this.urlProducer(`${environment.lobbyService.api.sessions}/${id}`))
        .then((session) => {
            return sessionFactory(session, id);
        });
    }

    public getGameServices(aborter?: AbortController) {
        return this.client.get<MinimalGameService[]>(this.urlProducer(environment.lobbyService.api.gameServices), undefined, aborter);
    }

    public createSession(username: string, serverName: string, saveGameId?: string) {
        return this.client.post<string>(this.urlProducer(environment.lobbyService.api.sessions), {
            data: {
                creator: username, 
                game: serverName, 
                savegame: saveGameId !== undefined && saveGameId !== null?saveGameId:""
            }
        })
    }
    public deleteSession(id: string) {
        return this.client.delete<ISession>(this.urlProducer(`${environment.lobbyService.api.sessions}/${id}`));
      }

    public launchSession(id:string){
        console.log(id);
        return this.client.post<void>(this.urlProducer(`${environment.lobbyService.api.sessions}/${id}`));
    }


    public leaveSession(sessionId: string, username: string ){
        return this.client.delete<void>(this.urlProducer(`${environment.lobbyService.api.sessions}/${sessionId}/${"players"}/${username}`))
    }

    public joinSession(sessionId: string, username: string){
        return this.client.put<void>(this.urlProducer(`${environment.lobbyService.api.sessions}/${sessionId}/${"players"}/${username}`));
    }

    public pollSession(sessions?: Session[], abort?: AbortController) {
        if (sessions === undefined)  {
            sessions = [];
        }
        let hashable: Record<string, ISession> = {}
        sessions.forEach(item => {
            hashable[item.id] = {
                gameParameters: item.gameParameters,
                creator: item.creator,
                players: item.players,
                launched: item.launched,
                savegameid: item.savegameid,
                playerLocations: item.playerLocations
            }
        });
        let myHash = JSON.stringify({sessions: hashable});
        let hash = MD5(myHash).toString();
        return this.client
            .get<{sessions: Record<string, ISession>}>(this.urlProducer(environment.lobbyService.api.sessions), {uriData: {hash: hash}}, abort)
            .then(({sessions}) => this.convertToObjects(sessions));
    }

    getUser(username: string, aborter?: AbortController) {
        return this.client.get<User>(this.urlProducer(`${environment.lobbyService.api.users}/${username}`), undefined, aborter);
    }
    
}