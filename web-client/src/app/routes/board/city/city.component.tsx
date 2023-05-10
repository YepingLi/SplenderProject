import React from "react";
import { BoardProps } from "../../../models/boardProps";
import {CityGameBoard, Meta} from "../../../models/game";
import { CardDisplayCity } from "../card-display/card-display.component";
import {getCities} from "../constants";
import { Splendor } from "../splendor/splendor.component";
import {Action} from "../../../models/move";
import { OrientRight } from "../orient/orient.component";

export function City(props: {board: CityGameBoard, service: string, updateAction:(action:Action)=>void}, isTurn: boolean) {

    let getCity = () => {
        return (meta: Meta) => getCities(props.service, meta.id!)
    }

    return (
        <div>
            <CardDisplayCity id={"cities"} urlProducer={getCity()} minimumCards={3} cards={props.board.cities} updateAction={props.updateAction}  isTurn = {isTurn}/>
        </div>
    )
}

export function CitiesOrient(props: BoardProps) {
    let board = props.game.board as CityGameBoard;
    return (
        <div>
            <Splendor {...props} 
                inBoard={<OrientRight board={board} service={props.service} actionUpdate={props.updateActions} isTurn={props.isTurn}/>}
                header={<City board={board} service={props.service} updateAction={props.updateActions}/>} />
        </div>
    )
}