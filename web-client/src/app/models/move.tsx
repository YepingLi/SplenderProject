import { ActionType } from "./game-types"

export interface Action {
    type: ActionType,
    value: any
}

export interface Move {
    actions: Action[]
}

export interface GameMove extends Move {
    actionId: number
}

function typeChecker(t: any) {
    return typeof t === 'string' || t instanceof String || typeof t === "number" || t instanceof Number;
}

function performCheckVersusInternal(v1: any, v2: any): boolean {
    function filterKey (k: string) {
        if (typeChecker(v1[k])) {
            return v1[k] === v2[k];
        }
        return performCheckVersusInternal(v1[k], v2[k]);
    }

    let keys = Object.keys(v1);
    return keys.filter(k => filterKey(k)).length === keys.length;
}

/**
 * Compare if 2 actions is the same.
 * @param action1 
 * @param action2 
 * @returns a boolean saying if they are the same.
 */
export function compareActions(action1 : Action, action2 : Action){
    if(action1.type !== action2.type)
        return false;
    if (typeChecker(action1.value)) {
        return action1.value === action2.value
    }
    return performCheckVersusInternal(action1.value, action2.value);
}

/**
 * compare if 2 moves is the same;
 * @param move1 
 * @param move2 
 * @returns one of the move if 2 moves are the same. move with empty actions if they're different
 */
export function CompareMoves(move1 : Move, move2 : Move){
    if(move1.actions.length !== move2.actions.length) {
        return null;
    }
    for (let num = 0; num < move1.actions.length; num ++) {
        if (move1.actions [num] === move2.actions[num]){
            continue
        } else {
            return null;
        }
    }
    return move1.actions;
}