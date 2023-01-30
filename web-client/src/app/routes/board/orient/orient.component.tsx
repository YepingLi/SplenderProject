import React from "react";
import GameBoard from "../../../models/game";


export function Orient(props: {game: GameBoard}) {
    return (
        <div>
            <div id="orient-lvl-3"></div>
            <div id="orient-lvl-2"></div>
            <div id="orient-lvl-1"></div>
        </div>
    )
}