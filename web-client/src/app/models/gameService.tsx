export interface MinimalGameService {
    location: string
    name: string
}


export default interface GameService extends MinimalGameService {
    minSessionPlayers: number
    maxSessionPlayers: number
    webSupport: boolean
}