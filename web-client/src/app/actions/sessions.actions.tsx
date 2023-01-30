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

export function addSessionReducer(state: SessionState, action: PayloadAction<Session>) {
    state.sessions = state.sessions.concat(action.payload);
    return state;
}

export function removeSessionReducer(state: SessionState, action: PayloadAction<string>) {
    state.sessions = state.sessions.filter((session) => session.id != action.payload);
    return state;
}

export const sessionSlice = createSlice({
    name: "sessions",
    initialState: initState(),
    reducers: {
        updateSessions: updateSessionsReducer,
        addSession: addSessionReducer,
        removeSession: removeSessionReducer
    }
});


export const sessionReducer = sessionSlice.reducer;
export const { updateSessions, addSession, removeSession } = sessionSlice.actions;
export const sessionSelector = (state: {sessions: SessionState}) => state.sessions;