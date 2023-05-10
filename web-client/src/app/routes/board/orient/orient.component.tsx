import React from "react";
import { BoardProps } from "../../../models/boardProps";
import { GameBoard, Meta, OrientGameBoard } from "../../../models/game";
import { CardDisplay } from "../card-display/card-display.component";
import { getCard } from "../constants";
import { Splendor } from "../splendor/splendor.component";
import { Action } from "../../../models/move";

/**
 * Builds the right hand side of the board
 * @param props 
 * @returns 
 */
export function OrientRight(props: { board: OrientGameBoard, service: string, actionUpdate: (action: Action) => void, isTurn: boolean}) {
    
    let getOrientCard = (meta: Meta) => getCard(props.service, meta);
    return (
        <div>
            <CardDisplay id="orient-lvl-3" reverse={true}
                cards={props.board.faceUpOrientCards.filter(obj => obj.meta?.level === 3)}
                urlProducer={getOrientCard} minimumCards={2}
                remainingCards={props.board.remainingLevelThreeOrientCards}
                updateAction={props.actionUpdate} isTurn={props.isTurn} />
            <CardDisplay id="orient-lvl-2" reverse={true}
                cards={props.board.faceUpOrientCards.filter(obj => obj.meta?.level === 2)}
                urlProducer={getOrientCard} minimumCards={2}
                remainingCards={props.board.remainingLevelTwoOrientCards}
                updateAction={props.actionUpdate} isTurn={props.isTurn} />
            <CardDisplay id="orient-lvl-1" reverse={true}
                cards={props.board.faceUpOrientCards.filter(obj => obj.meta?.level === 1)}
                urlProducer={getOrientCard} minimumCards={2}
                remainingCards={props.board.remainingLevelOneOrientCards}
                updateAction={props.actionUpdate} isTurn={props.isTurn}
            />
        </div>
    )
}


export function Orient(props: BoardProps) {
    let board = props.game.board as OrientGameBoard;
    return (
        <div>
            <Splendor {...props} inBoard={<OrientRight board={board} service={props.service} actionUpdate={props.updateActions} isTurn={props.isTurn}/>} />
        </div>
    )
}