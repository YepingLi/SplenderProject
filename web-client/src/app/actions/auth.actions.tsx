import { createSlice } from "@reduxjs/toolkit";
import { TokenResponse } from "../../auth/TokenResponse";

export type Token = {
    token: string,
    refresh: string,
    isAuthed: boolean
}

function getTokens(): Token {
    let access = localStorage.getItem("access_token");
    let refresh = localStorage.getItem("refresh_token");
    if (refresh == null && access != null) {
        throw new Error("Refresh is null but access is not. Invalid state.");
    }
    
    if (access == null) {
        access = "";
    }
    
    if (refresh == null) {
        refresh = "";
    }

    return {token: access, refresh: refresh, isAuthed: false}
}

export const authSlice = createSlice({
    name: "authentication",
    initialState: getTokens(),
    reducers: {
        auth: (state, newTokens: {payload: TokenResponse, type: string}) => {
            localStorage.setItem("access_token", newTokens.payload.access_token);
            localStorage.setItem("refresh_token", newTokens.payload.refresh_token);
            state.refresh = newTokens.payload.refresh_token;
            state.token = newTokens.payload.access_token;
        }
    }
});

export const authReducer = authSlice.reducer;
export const authSelector = (state: {authentication: Token}) => state.authentication;
