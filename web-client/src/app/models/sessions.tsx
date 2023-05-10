import GameService from "./gameService"


export interface ISession {
    creator: string
    gameParameters: GameService
    launched: boolean
    players: string[]
    savegameid: string
    playerLocations: Record<string, string>
}

export default interface Session extends ISession {
    creator: string
    gameParameters: GameService
    launched: boolean
    players: string[]
    savegameid: string
    id: string
}

export function sessionFactory(session: ISession, id: string): Session {
    return {...session, id: id}
}