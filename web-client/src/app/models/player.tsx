import { GemTypeString, Bonus, Power } from "./game-types"
import {GameCard} from "./game";

export default interface Player {
    name: string
    colour: string
    gems: Record<GemTypeString, number>
    bonuses: Record<Bonus, number>
    powers: Power[]
    reservedCards: GameCard[]
    playerPrestige: number
    points: number
    reservedNobles: GameCard[]
}