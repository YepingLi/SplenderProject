import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import GameBoard from "../models/game";
import Move from "../models/moves";

type GameState = {
    game: GameBoard;
    moves: Move[];
}


function initState(): GameState {
    return {
        game: {
            nobles: [],
            levelOneCards: [],
            levelThreeCards: [],
            levelTwoCards: [],
            players: [],
            freeGems: []
        },
        moves: [] 
    };
}

function updateGameReducer(state: GameState, action: PayloadAction<GameBoard>) {
    state.game = action.payload;
    return state;
}

function possibleMovesReducer(state: GameState, moves: PayloadAction<Move[]>) {
    state.moves = moves.payload;
    return state;
}

export const gameStateSlice = createSlice({
    name: "gameState",
    initialState: initState(),
    reducers: {
        updateGame: updateGameReducer,
        updateMoves: possibleMovesReducer
    }
});

export const gameSelector = (state: { gameState: GameState }) => state.gameState;
export const gameStateReducer = gameStateSlice.reducer;