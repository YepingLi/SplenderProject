import React from "react";
import GameBoard from "../../../models/game";


export function PlayerArea(props: {username: string, game: GameBoard}) {
    return (
        <div>
            Welcome {props.username} to Player Area
        </div>
    )
} 