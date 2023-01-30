import { environment } from "../../environment/environment";
import { MinimalGameService } from "../models/gameService";
import { ISession, sessionFactory } from "../models/sessions";
import { BaseService } from "./base.service";

export class SessionService extends BaseService {

    constructor() {
        super(environment.lobbyService.getApiUrl());
    }

    public getSessions() {
        return this.get<{sessions: Record<string, ISession>}>(environment.lobbyService.api.sessions)
            .then(({sessions}) => {
                return Object.keys(sessions).map(key => sessionFactory(sessions[key], key))
            });
    }

    public getSession(id: string) {
        return this.get<ISession>(`${environment.lobbyService.api.sessions}/${id}`)
        .then((session) => {
            return sessionFactory(session, id);
        });
    }

    public getGameServices() {
        return this.get<MinimalGameService[]>(environment.lobbyService.api.gameServices);
    }

    public createSession(username: string, serverName: string, saveGameId?: string) {
        return this.post<string>(environment.lobbyService.api.sessions, {
            data: {
                creator: username, 
                game: serverName, 
                savegame: saveGameId !== undefined && saveGameId !== null?saveGameId:""
            }
        })
    }
    public deleteSession(id: string) {
        return this.delete<ISession>(`${environment.lobbyService.api.sessions}/${id}`);
      }

    public launchSession(id:string){
        console.log(id);
        return this.post<ISession>(`${environment.lobbyService.api.sessions}/${id}`);
    }

    public leaveSession(sessionId: string, username: string ){
        return this.delete<ISession>(`${environment.lobbyService.api.sessions}/${sessionId}/${"players"}/${username}`)
    }

    public joinSession(sessionId: string, username: string){
        return this.put<ISession>(`${environment.lobbyService.api.sessions}/${sessionId}/${"players"}/${username}`);
    }
}