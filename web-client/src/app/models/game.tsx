import Session from "./sessions";
import {GameService} from "../services/game.service";
import Player from "./player";
import { GemType, GemTypeString } from "./game-types";

export interface CardDimension {
    height: number | string;
    width: number | string;
}

export interface Meta {
    id?: number;
    level? : number;
    type? : string;
}

export interface GameCard {
    cardBack?: boolean;
    isFree?: boolean;
    position: number;
    meta? : Meta;
    [key: string]: any;
}



export interface Gem {
    id: string
    type: GemTypeString
}

export interface GameBoard {
    remainingLevelOneCards: number
    remainingLevelTwoCards: number
    remainingLevelThreeCards: number
    faceUpCards : GameCard[]
    nobles: GameCard[]
    players: string[]
    availableGems: Record<GemTypeString, number>
}

export interface OrientGameBoard extends GameBoard {
    remainingLevelOneOrientCards: number
    remainingLevelTwoOrientCards: number
    remainingLevelThreeOrientCards: number
    faceUpOrientCards: GameCard[]
}
export interface TradingPostsGameBoard extends GameBoard{
    remainingLevelOneOrientCards: number
    remainingLevelTwoOrientCards: number
    remainingLevelThreeOrientCards: number
    faceUpOrientCards: GameCard[]
    tradingPosts: GameCard[]
}
export interface CityGameBoard extends GameBoard{
    remainingLevelOneOrientCards: number
    remainingLevelTwoOrientCards: number
    remainingLevelThreeOrientCards: number
    faceUpOrientCards: GameCard[]
    cities: GameCard[]
}
export default interface Game {
    board: GameBoard
    type: "BASE" | "TRADING_POSTS" | "ORIENT" | "CITIES_ORIENT";
    creator: string;
    players: Player[];
    gameServer: string
    curPlayer: Player;
    isOver: boolean;
}


export function emptyCard(value: number): GameCard {
    return {
        position: value
    }
}

export function isGameStarted() {

}