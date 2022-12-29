import { configureStore } from "@reduxjs/toolkit";
import { authReducer } from "./actions/auth.actions";

export const appStore = configureStore({
    reducer: {
        authentication: authReducer
    }
});