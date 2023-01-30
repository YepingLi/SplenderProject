import { configureStore } from "@reduxjs/toolkit";
import { sessionReducer } from "./actions/sessions.actions";
import { gameStateReducer } from "./actions/board.actions";
import { gameServiceReducer } from "./actions/game-services.actions";
import { toastReducer } from "./actions/toast.actions";

/**
 * All the reducers are added here, to the global store.
 * The data all ends up in the store to be easily updated and kept track of.
 */
export const appStore = configureStore({
    reducer: {
        sessions: sessionReducer,
        gameState: gameStateReducer,
        gameService: gameServiceReducer,
        toast: toastReducer
    }
});