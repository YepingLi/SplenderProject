import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import Game, { Meta } from "../models/game";
import { GemTypeString, Bonus, ActionType } from "../models/game-types";
import { Action, compareActions, GameMove, Move } from "../models/move";
import { MD5 } from "crypto-js";
import { BoardModalType } from "../models/render-types";

type GameState = {
    game?: Game;
    moves: GameMove[];
    //remainingPossibleMoves?: GameMove[]; //Added so that we don't need to search through all moves on every action.
    currentMove?: Move,
    hashableGame?: {
        game: Game,
        hash: string
    }
}

export interface UpdateGameState {
    game: Game,
    moves: GameMove[]
}

export class MoveError extends Error {
    constructor(msg: string) {
        super(msg);
    }
}

export interface ValidMove {
    requiresNext: boolean;
    msg: string;
    actionId?: number;
    modal?: BoardModalType
}


function initState(): GameState {
    return {
        moves: [],
    };
}

//Updates the game state given an action.
function updateGameReducer(state: GameState, action: PayloadAction<Game>) {
    state.game = action.payload;
    //state.remainingPossibleMoves = state.remainingPossibleMoves; //Each action updates the remainingPossibleMoves
    return state;
}

//Updates the possible Moves.
function possibleMovesReducer(state: GameState, moves: PayloadAction<GameMove[]>) {
    state.moves = moves.payload;
    return state;
}



//Checks if the move requires the user to specify how they want to pay (due to Gold Tokens)
function requiresPaymentSpecification(move: GameMove | undefined) {
    if (move === undefined) {
        return false;
    }
    for (let i = 0; i < move.actions.length; i++) {
        const action = move.actions.at(i);
        if (action === undefined) {
            return false;
        }
        else {
            if (action.type.toString() == "PAYMENT")
                return true;
        }
    }
    return false;
}

function compareMeta(meta1: Meta, meta2: Meta) {
    return meta1.type === meta2.type && meta1.level === meta2.level && meta1.id === meta2.id;
}

function updateBoardGivenAction(state: GameState, action: Action) {
    // deep copy hack
    let newGame: Game = JSON.parse(JSON.stringify(state.game!));
    switch (action.type) {
        case "TAKE_TOKEN":
            let value: GemTypeString = (action.value as string).toUpperCase() as GemTypeString;
            newGame.board!.availableGems[value] = newGame.board?.availableGems[value]! - 1;
            return newGame;
        case "RESERVE_CARD":
            let reserveMeta: Meta = action.value as Meta;
            //TODO: Fix this so the cards are dealt correctly.
            newGame!.board!.faceUpCards = newGame.board?.faceUpCards.filter(card => !compareMeta(card.meta!, reserveMeta));
            return newGame;
        case "BUY_CARD":
            let purchaseMeta: Meta = action.value as Meta;
            newGame!.board!.faceUpCards = newGame.board?.faceUpCards.filter(card => !compareMeta(card.meta!, purchaseMeta));
            return newGame;
        case "BURN_TOKEN":
            let curToken: GemTypeString = (action.value as string).toUpperCase() as GemTypeString;
            newGame.curPlayer.gems[curToken] = newGame.curPlayer.gems[curToken]! - 1;
            return newGame;
        case "LEVEL_ONE_PAIR" || "LEVEL_TWO_PAIR":
            let curBonus: Bonus = (action.value as string).toUpperCase() as Bonus;
            newGame.curPlayer.bonuses[curBonus] = newGame.curPlayer.bonuses[curBonus]! + 1;
            return newGame;
        case "TAKE_LEVEL_ONE":

    }
    return newGame;
}

//Helper function for filtering Payments. Counts the number of payments of a certain type.
function countActionsByType(actions: Action[]): { [type: string]: number } {
    const counts: { [type: string]: number } = {};
    for (const action of actions) {
        if (action.type === "PAYMENT") {
            let value = action.value
            counts[value] = (counts[value] || 0) + 1;
        }

    }
    return counts;
}

//A helper function which tells you if an action is in a move.
function contains(moves: GameMove[], type: ActionType, index: number) {
    for (const curMove of moves) {
        for (let i = index; i < curMove.actions.length; i++) {
            if (i >= curMove.actions.length) {
                continue;
            }
            if (curMove.actions[i].type === type) {
                return true;
            }
        }
    }
    return false;
}

export function findMoves(moves: GameMove[], newMove: Move) {
    return moves.filter(move => {
        if (move.actions.length < newMove.actions.length) {
            return false;
        }
        for (let i = 0; i < newMove.actions.length; i++) {
            let newAction = newMove.actions[i];
            let possible = move.actions[i];
            if (!compareActions(newAction, possible)) {
                return false;
            }
        }

        return true;
    })
}

export function isPossibleMove(state: GameState, action: Action): ValidMove {
    let actions: Action[] = new Array<Action>(action);
    if (state.currentMove !== undefined) {
        actions = state.currentMove?.actions.map(a => a).concat(actions);
    }
    let newMove: Move = { actions: actions };
    let possibleMoves: GameMove[] = [];
    let initialPossibleMoves = findMoves(state.moves, newMove);
    if (initialPossibleMoves.length === 1 && initialPossibleMoves[0].actions.length === newMove.actions.length) {
        return {msg: 'Finalize your move', requiresNext: false, actionId: initialPossibleMoves[0].actionId}
    }
    //IF THE MOVE IS A PURCHASING MOVE WHICH REQUIRES PAYMENT.
    if (newMove.actions.length > 1 && newMove.actions[0].type === "BUY_CARD" && (contains(initialPossibleMoves, "PAYMENT", newMove.actions.length - 1) || contains(initialPossibleMoves, "DOUBLE_GOLD", newMove.actions.length - 1))) {
        possibleMoves = initialPossibleMoves.filter(move => {
            if (move.actions.length < newMove.actions.length) {
                return false;
            }
            const newActionsByType = countActionsByType(newMove.actions);
            const moveActionsByType = countActionsByType(move.actions);

            for (const [type, count] of Object.entries(newActionsByType)) {
                if (moveActionsByType[type] < count) {
                    return false;
                }
            }
            for (let i = 0; i < newMove.actions.length; i++) {
                let newAction = newMove.actions[i];
                let actionFound = false;
                for (let j = 0; j < move.actions.length; j++) {
                    let possibleAction = move.actions[j];
                    if (compareActions(newAction, possibleAction)) {
                        actionFound = true;
                        break;
                    }
                }
                if (!actionFound) {
                    return false;
                }
            }
            return true;
        });
    }
    //CASE OF AN ORIENT CARD WHOSE HAS ALREADY BEEN PAID.
    else if (newMove.actions.length > 1 && newMove.actions[0].type === "BUY_CARD" && !contains(initialPossibleMoves, "PAYMENT", newMove.actions.length - 1)) {
        possibleMoves = initialPossibleMoves.filter(move => {
            if (move.actions.length < newMove.actions.length) {
                return false;
            }
            let possibleAction = move.actions[newMove.actions.length - 1];
            let performed = newMove.actions[newMove.actions.length - 1];
            return compareActions(possibleAction, performed);
        });
    }
    else {
        possibleMoves = state.moves.filter(move => {
            if (move.actions.length < newMove.actions.length) {
                return false;
            }
            for (let i = 0; i < newMove.actions.length; i++) {
                let possibleAction = move.actions[i];
                let performed = newMove.actions[i];
                if (!compareActions(possibleAction, performed)) {
                    return false;
                }
            }
            return true;
        });

    }

    //Error, delete this action from the move.
    if (possibleMoves.length === 0) {
        throw new MoveError(`${action.type}  :an illegal move`);
    }



    function doneCollectToken(){
        return newMove.actions[0].type === "BURN_TOKEN"
    }
    
    if (doneCollectToken()) {
        return requiresNext("ADDED A " + action.value + " TO YOUR MOVE", "BURN_TOKEN");
    }



    function requiresNext(msg: string, modal?: BoardModalType): ValidMove {
        let hasNext = possibleMoves.length > 1 || possibleMoves[0].actions.length !== newMove.actions.length;
        return { msg, requiresNext: hasNext, actionId: hasNext ? undefined : possibleMoves[0].actionId, modal: modal };
    }

    if (possibleMoves.length === 1) {
       return {msg: 'Finalize your move', requiresNext: false, actionId: initialPossibleMoves[0].actionId}
    }


    //MAKE SOME TOASTS:
    if (possibleMoves.length >= 1 && action.type === "TAKE_TOKEN") {
        return requiresNext("ADDED A " + action.value + " TO YOUR MOVE");
    
    }

    if (contains(possibleMoves, "PAYMENT", 1) && action.type == "BUY_CARD") {
        return requiresNext("PLEASE CHOOSE HOW YOU WOULD LIKE TO PAY", "PICK_PAYMENT");
    }

    if (possibleMoves.length > 1 && newMove.actions[0].type === "TAKE_TOKEN"  && !contains(possibleMoves, "TAKE_TOKEN", newMove.actions.length)) {
        const nextMove = (title: ActionType) => {
           return contains(possibleMoves, title, newMove.actions.length);
        }
        if (nextMove("BURN_TOKEN")) {
            return requiresNext("PLEASE SELECT A TOKEN TO BURN");
        }
    }
    //If The Card Has Been Purchased and requires Further Action From the User.
    if (possibleMoves.length > 1 && newMove.actions[0].type === "BUY_CARD" && !contains(possibleMoves, "PAYMENT", newMove.actions.length)) {
        const nextMove = (title: ActionType) => {
            return contains(possibleMoves, title, newMove.actions.length);
        }    
        if (nextMove("TAKE_LEVEL_TWO")) {
                return requiresNext("PLEASE SELECT A FREE LEVEL TWO CARD");
            }
            else if (nextMove("RESERVE_NOBLE")) {
                return requiresNext("PLEASE SELECT A NOBLE TO RESERVE");
            }
            else if (nextMove("LEVEL_TWO_PAIR")) {
                return requiresNext("PLEASE SELECT A BONUS FOR PAIRING");
            }
            else if (nextMove("TAKE_LEVEL_ONE")) {
                return requiresNext("PLEASE SELECT A FREE LEVEL ONE CARD");
            }
            else if (nextMove("LEVEL_ONE_PAIR")) {
                return requiresNext("(Lvl 1) PLEASE SELECT A BONUS FOR PAIRING");
            }
            else if (nextMove("TAKE_TOKEN")) {
                return requiresNext("PLEASE SELECT A GEM");
            }
            else if (nextMove("CLAIM_NOBLE")) {
                return requiresNext("Add a noble to your move!", "CLAIM_NOBLE");
            }
        }

    throw new MoveError("Failed to find ending...");
}

function updateMoveReducer(state: GameState, action: PayloadAction<Action>) {
    if (state.currentMove === undefined) {
        state.currentMove = { actions: [] }
    }

    state.currentMove.actions.push(action.payload);
    state.game = updateBoardGivenAction(state, action.payload);
    return state;
}

function resetMoveReducer(state: GameState, _: PayloadAction<undefined>) {
    state.currentMove = { actions: [] }
    state.game = JSON.parse(JSON.stringify(state.hashableGame?.game));
    return state;
}

function updateGameStateReducer(_: GameState, action: PayloadAction<UpdateGameState>): GameState {
    return {
        game: action.payload.game,
        hashableGame: {
            game: action.payload.game,
            hash: MD5(JSON.stringify(action.payload.game)).toString()
        },
        moves: action.payload.moves,
    };
}

export const gameStateSlice = createSlice({
    name: "gameState",
    initialState: initState(),
    reducers: {
        updateGame: updateGameReducer,
        updateMoves: possibleMovesReducer,
        updateMove: updateMoveReducer,
        resetMove: resetMoveReducer,
        updateGameState: updateGameStateReducer
    }
});

export const gameSelector = (state: { gameState: GameState }) => state.gameState;
export const gameStateReducer = gameStateSlice.reducer;
export const {
    updateGame,
    updateMoves,
    updateMove,
    resetMove,
    updateGameState
} = gameStateSlice.actions;