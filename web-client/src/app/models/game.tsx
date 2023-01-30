
export interface CardDimension {
    height: number;
    width: number;
}

export interface GameCard {
    position: number;
    name?: string;
}

export enum GemType {
    DIAMOND, EMERALD, ONYX, RUBY, SAPPHIRE, GOLD
}

export type GemTypeString = "DIAMOND" | "EMERALD" | "ONYX" | "RUBY" | "SAPPHIRE" | "GOLD";

export interface Gem {
    id: string
    type: "DIAMOND" | "EMERALD" | "ONYX" | "RUBY" | "SAPPHIRE" | "GOLD"
}

export default interface GameBoard {
    nobles: GameCard[]
    levelOneCards: GameCard[]
    levelTwoCards: GameCard[]
    levelThreeCards: GameCard[]
    players: string[]
    freeGems: Gem[]
}

export function emptyCard(value: number): GameCard {
    return {
        position: value
    }
}

export function isGameStarted() {

}