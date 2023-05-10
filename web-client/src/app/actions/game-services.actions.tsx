import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { MinimalGameService } from "../models/gameService";

type GameServiceState = {
    services: MinimalGameService[]
}

function updateGameServicesReducer(state: GameServiceState, action: PayloadAction<MinimalGameService[]>) {
    state.services = action.payload
    return state;
}


const gameServiceSlice = createSlice({
    name: "gameServices",
    initialState: {
        services: []
    } as GameServiceState,
    reducers: {
        updateGameServices: updateGameServicesReducer
    }
});

export const gameServiceSelector = (state: {gameService: GameServiceState}) => state.gameService;
export const gameServiceReducer = gameServiceSlice.reducer;
export const { updateGameServices } = gameServiceSlice.actions;