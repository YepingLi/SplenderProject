import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import Session from "../models/sessions";

type SessionState = {
    sessions: Session[]
}

function initState(): SessionState {
    return {sessions: []};
}

function updateSessionsReducer(state: SessionState, action: PayloadAction<Session[]>) {
    state.sessions = action.payload;
    return state;
}

export const sessionSlice = createSlice({
    name: "sessions",
    initialState: initState(),
    reducers: {
        updateSessions: updateSessionsReducer,
    }
});


export const sessionReducer = sessionSlice.reducer;
export const { updateSessions } = sessionSlice.actions;
export const sessionSelector = (state: {sessions: SessionState}) => state.sessions;