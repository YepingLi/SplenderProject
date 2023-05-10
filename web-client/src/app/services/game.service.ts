import { MD5 } from "crypto-js";
import { HttpClient, PostableData } from "../../shared/httpClient";
import Game, { GameBoard } from "../models/game";
import { GameMove, Move } from "../models/move";
import Session from "../models/sessions";
import { service } from "./service";
import {getToken} from "../routes/board/constants";

export class GameService {
    private client: HttpClient;
    constructor() {
        this.client = service.client;
    }

    private buildData(hashable?: string) {
        if (hashable !== undefined) {
            return {uriData: {game_hash: hashable}}
        }
        return undefined;
    }

    public getGame(session: Session, hash?: string, abort?: AbortController) {
        const location = session.gameParameters.location;
        const url = `${location}/${session.id}/currentGame`;
        return this.client.get<Game>(url, this.buildData(hash), abort);
    }

    public getMoves(session: Session) {
        const location = session.gameParameters.location;
        const url = `${location}/games/${session.id}/move`;
        return this.client.get<Move[]>(url)
            .then(moves => moves.map((move, i) => {
                return {...move, actionId: i} as GameMove;
            }));
    }

    public putMove(session: Session, moveId: number) {
        const location = session.gameParameters.location;
        const url = `${location}/games/${session.id}/move`;
        return this.client.put<void>(url, {data: {moveId: moveId}});
    }
    public saveGame(session: Session){
        const location = session.gameParameters.location;
        const url = `${location}/api/games/${session.id}`;
        return this.client.post<void>(url);
    }
}