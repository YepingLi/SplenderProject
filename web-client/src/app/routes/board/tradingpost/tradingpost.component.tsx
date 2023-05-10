import { Meta, OrientGameBoard, TradingPostsGameBoard } from "../../../models/game";
import { getCard, getTradingPost } from "../constants";
import { CardDisplay, CardDisplayTradingPost } from "../card-display/card-display.component";
import { BoardProps } from "../../../models/boardProps";
import { Splendor } from "../splendor/splendor.component";
import React from "react";
import { Action } from "../../../models/move";

/**
 * Builds the right hand side of the board
 * @param props
 * @returns
 */

export function TradingPost(props: { board: TradingPostsGameBoard, service: string, updateAction: (action: Action) => void }, isTurn: boolean) {

    let getTradingPostCard = () => {
        return (meta: Meta) => getTradingPost(props.service, meta.id!);
    }

    return (
        <div>
            <CardDisplayTradingPost id={"tradingPosts"} urlProducer={getTradingPostCard()} minimumCards={5} cards={props.board.tradingPosts} updateAction={props.updateAction} isTurn={isTurn} />
        </div>
    )
}
export function OrientRight(props: { board: OrientGameBoard, service: string, actionUpdate: (action: Action) => void }, isTurn: boolean) {

    let getOrientCard = (meta: Meta) => getCard(props.service, meta);

    return (
        <div>
            <CardDisplay id="orient-lvl-3" reverse={true}
                cards={props.board.faceUpOrientCards.filter(obj => obj.meta?.level === 3)}
                urlProducer={getOrientCard} minimumCards={2}
                remainingCards={props.board.remainingLevelThreeOrientCards}
                updateAction={props.actionUpdate} isTurn={isTurn} />
            <CardDisplay id="orient-lvl-2" reverse={true}
                cards={props.board.faceUpOrientCards.filter(obj => obj.meta?.level === 2)}
                urlProducer={getOrientCard} minimumCards={2}
                remainingCards={props.board.remainingLevelTwoOrientCards}
                updateAction={props.actionUpdate} isTurn={isTurn} />
            <CardDisplay id="orient-lvl-1" reverse={true}
                cards={props.board.faceUpOrientCards.filter(obj => obj.meta?.level === 1)}
                urlProducer={getOrientCard} minimumCards={2}
                remainingCards={props.board.remainingLevelOneOrientCards}
                updateAction={props.actionUpdate} isTurn={isTurn}
            />
        </div>
    )
}


export function TradingPosts(props: BoardProps) {
    let board = props.game.board as TradingPostsGameBoard;
    return (
        <div>
            <Splendor {...props}
                inBoard={<OrientRight board={board} service={props.service} actionUpdate={props.updateActions} />}
                header={<TradingPost board={board} service={props.service} updateAction={props.updateActions} />} />
        </div>
    )
}