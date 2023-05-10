import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import Toast, { MinimalToastInformation } from "../models/toast";

type ToastState = {
    toast: Toast[]
}

var counter = 0;

function handleCounter() {
    return counter++;
}

function resetCounter() {
    counter = 0;
}

const store: ToastState = {
    toast: []
}


const toastSlice = createSlice({
    name: "toast",
    initialState: store,
    reducers: {
        addToast: (state: ToastState, action: PayloadAction<MinimalToastInformation>) => {
            const {msg, source} = action.payload;
            state.toast = state.toast.concat({id: handleCounter(), msg: msg, source: source}); 
            return state;
        },
        removeToast: (state: ToastState, action: PayloadAction<number>) => {
            state.toast = state.toast.filter((toast) => toast.id !== action.payload);
            if (state.toast.length === 0) {
                resetCounter();
            }
            return state;
        }
    }
});

export const toastSelector = (state: {toast: ToastState}) => state.toast;
export const { addToast, removeToast } = toastSlice.actions;
export const toastReducer = toastSlice.reducer;