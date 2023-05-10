import {CardDimension, emptyCard, GameCard} from "../../../models/game";
import { CardUrlProducer} from "../../../models/types";
import {Action} from "../../../models/move";
import React, {useState} from "react";
import {Card} from "react-bootstrap";

const SIZE: CardDimension = {height: 100, width: 50};

type CardDisplayProps = {
    id: string,
    remainingCards?: number,
    cards: GameCard[],
    urlProducer: CardUrlProducer,
    dimension?: CardDimension,
    cardBack?: boolean,
    minimumCards: number
    isNoble?: boolean,
    reverse?: boolean,
    updateAction: (action: Action) => void,
    isTurn: boolean,
    reservedCardsNumber?:number
}

type CardGeneratorProps = {
    card: GameCard,
    dimensions: CardDimension,
    urlProducer: CardUrlProducer,
    id: string,
    isNoble?: boolean,
    isBack?: boolean,
    isFree?: boolean,
    remainingCards?: number,
    hasActions: boolean,
    updateAction: (action: Action) => void,
    isTurn: boolean
}

export function ReservedCardComponents(props: CardGeneratorProps) {
    const [displayCardActions, setDisplayCardActions] = useState(false);
    const {card, dimensions, urlProducer, id, isNoble, isBack, remainingCards, hasActions, isTurn} = props;
    const noDisplayActions = hasActions === false;

    //TODO: Only display the options if the player is eligible.
    function handleClick() {
        setDisplayCardActions(!displayCardActions);
    }

    function buyHandleClick(props: CardGeneratorProps) {
        let action: Action = {
            type: "BUY_CARD",
            value: props.card.meta!
        }
        console.log(props.card.meta!);
        props.updateAction!(action);
        setDisplayCardActions(!displayCardActions);
    }
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
            styling.backgroundImage = `url(${urlProducer(card.meta)})`;
        }
    }
    if (card.meta?.id !== undefined) {
        styling.backgroundImage = `url(${urlProducer(card.meta)})`;
    }
    if (card.meta?.id === 0 && !isNoble) {
        styling.backgroundImage = `url(${urlProducer(card.meta)})`;
        cardsLeft = remainingCards;
    }
    return (
        <Card key={`card-${card.position}-${id}`} style={styling} onClick={handleClick} className="game-card">
            <Card.Body>
                {
                    !isBack && (<div>
                        {displayCardActions && !noDisplayActions &&
                            <button disabled={!isTurn} onClick={() => buyHandleClick(props)}>Purchase</button>}
                    </div>)
                }
                <Card.Text className={'card-text'}> {cardsLeft} </Card.Text>
            </Card.Body>
        </Card>
    )
}

export function ReservedCardDisplay(props: CardDisplayProps) {
    let dimensions = props.dimension ? props.dimension : SIZE;
    let cards = [...props.cards]
        .sort((aCard, bCard) => aCard.position - bCard.position)
        .map((card, i) => (
            <ReservedCardComponents key={`${card.meta}-${card.meta?.level}-${i}`}
                           card={card} isBack={card.cardBack} isFree = {card.isFree}  dimensions={dimensions}
                           urlProducer={props.urlProducer} id={props.id} isNoble={props.isNoble} hasActions={props.isNoble !== true}
                           remainingCards={props.remainingCards} updateAction={props.updateAction} isTurn = {props.isTurn}/>
        ));
    return (
        <div id={props.id} className="card-holder">
            {cards}
        </div>
    )
}