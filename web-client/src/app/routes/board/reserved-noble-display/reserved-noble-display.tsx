import React, { useState } from "react";
import { CardDimension, GameCard, emptyCard } from "../../../models/game";
import { Card } from "react-bootstrap";
import { UrlProducer } from "../../../models/types";

import {Action} from "../../../models/move";


const SIZE: CardDimension = {height: 75, width: 75};
type NobleDisplayProps = {
    id: string,
    nobles: GameCard[],
    urlProducer: UrlProducer,
    dimension?: CardDimension,
    minimumCards: number
    updateAction?:(action:Action)=>void,
    isTurn: boolean
}

type NobleGeneratorProps = {
    card: GameCard,
    dimensions: CardDimension,
    urlProducer: UrlProducer,
    id: string,
    isNoble?: boolean,
    isBack?: boolean,
    isFree?: boolean,
    remainingCards?: number,
    hasActions: boolean,
    updateAction?:(action:Action)=>void,
    isTurn:boolean
}

export function ReservedCardComponents(props: NobleGeneratorProps) {
    //const [displayNobleActions, setDisplayNobleActions] = useState(false);
    const {card, dimensions, urlProducer, id, isNoble, isBack, remainingCards, hasActions, isTurn} = props;
    const noDisplayActions = hasActions === false;

    //TODO: Only display the options if the player is eligible.
    // function handleClick() {
    //     setDisplayNobleActions(!displayNobleActions);
    // }

    // function reserveHandleClick(props: NobleGeneratorProps) {
    //     let action: Action = {
    //         type: "RESERVENOBLE",
    //         value: props.card.meta?.id
    //     }
    //     props.updateAction!(action);
    //     //setDisplayNobleActions(!displayNobleActions);
    // }
    let styling: React.CSSProperties = {
        backgroundSize: "100% 100%",
        backgroundColor: 'gray',
        padding: "10px",
        margin: "[1,10]",
        ...dimensions
    }

    let cardsLeft = null;
    if (isNoble) {
        styling.width = styling.height;
        if (card.meta?.id !== undefined) {
            styling.backgroundImage = `url(${urlProducer(card.meta.id)})`;
        }
    }
    if (card.meta?.id !== undefined) {
        styling.backgroundImage = `url(${urlProducer(card.meta.id)})`;
    }
    if (card.meta?.id === 0 && !isNoble) {
        styling.backgroundImage = `url(${urlProducer(card.meta.id)})`;
        cardsLeft = remainingCards;
    }
    return (
        <Card key={`card-${card.position}-${id}`} style={styling} className="game-reserve-noble">
            <Card.Body>
                <Card.Text className={'reserve-noble-text'}> {cardsLeft} </Card.Text>
            </Card.Body>
        </Card>
    )
}


export function ReservedNobleDisplay(props: NobleDisplayProps) {
    let dimensions = props.dimension ? props.dimension : SIZE;
    let preBuilt: GameCard[] = [];

    props.nobles.forEach(noble => {
        if (noble.meta) {
            preBuilt.push(noble);
        }
    });

    let cards = preBuilt
        .map((card, i) => (
            <ReservedCardComponents key={`${card.meta?.id}-${card.meta?.level}-${i}`}
                card={card} isBack={card.cardBack} isFree = {card.isFree}  dimensions={dimensions}
                urlProducer={props.urlProducer} id={props.id} hasActions={true}
                updateAction={props.updateAction} isTurn = {props.isTurn}/>
        ));
    return (
        <div id={props.id} className="card-holder">
            {cards}
        </div>
    )
}