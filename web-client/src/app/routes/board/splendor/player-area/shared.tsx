import { GemTypeString } from "../../../../models/game-types";
import { Action } from "../../../../models/move";

export type Props = {
    service: string,
    className: string,
    actionUpdate: (action: Action) => void,
    owned: Record<GemTypeString, number>,
    isTurn: boolean
}
