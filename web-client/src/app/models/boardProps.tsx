import Game from "./game"
import { CardUrlProducer } from "./types"
import {Action} from "./move";
import { TurnActions } from "../routes/board/splendor/player-area/player-area.component";

export interface BoardProps {
    game: Game
    getCard: (service: string) => CardUrlProducer
    getNoble: (service: string, id: number) => string
    service: string
    username: string
    updateActions : (action :Action)=> void
    isTurn : boolean
    isLevelOne : boolean
    turnActions: TurnActions
}

export interface SplendorProps extends BoardProps {
    inBoard?: JSX.Element
    header?: JSX.Element
}