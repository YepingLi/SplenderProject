import React, { useState } from "react";
import { CardDimension, GameCard, emptyCard } from "../../../models/game";
import { Badge, Button, Card, Modal, ModalBody } from "react-bootstrap";
import { CardUrlProducer } from "../../../models/types";
import "./card-display.component.scss";
import { Action } from "../../../models/move";


const SIZE: CardDimension = { height: 125, width: 75 };
const SIZE_city: CardDimension = {height: 125, width: 230};
const SIZE_tradingPost: CardDimension = {height: 125, width: 100};
type CardDisplayProps = {
    id: string,
    remainingCards?: number,
    cards: GameCard[],
    urlProducer: CardUrlProducer,
    dimension?: CardDimension,
    cardBack?: boolean,
    minimumCards: number
    reverse?: boolean,
    updateAction?: (action: Action) => void,
    isTurn: boolean
}

type CardGeneratorProps = {
    card: GameCard,
    dimensions: CardDimension,
    urlProducer: CardUrlProducer,
    id: string,
    isBack?: boolean,
    isFree?: boolean,
    remainingCards?: number,
    hasActions: boolean,
    updateAction?: (action: Action) => void,
    isTurn: boolean, hasModal?: boolean
}

function CardComponent(props: CardGeneratorProps) {

    const [displayCardActions, setDisplayCardActions] = useState(false);
    const { card, dimensions, urlProducer, id, isBack, remainingCards, hasActions, isTurn, hasModal } = props;
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
        props.updateAction!(action);
        setDisplayCardActions(!displayCardActions);
    }
    function reserveHandleClick(props: CardGeneratorProps) {
        let action: Action = {
            type: "RESERVE_CARD",
            value: props.card.meta!
        }

        props.updateAction!(action);
        setDisplayCardActions(!displayCardActions);
    }
    function takeHandleClick(props:CardGeneratorProps){
        if (props.card.meta!.level===2) {
            let action:Action = {
                type: "TAKE_LEVEL_TWO",
                value: props.card.meta!
            };
            props.updateAction?.(action);
            setDisplayCardActions(!displayCardActions);
        } else {
            let action:Action = {
                type: "TAKE_LEVEL_ONE",
                value: props.card.meta!
            };
            props.updateAction?.(action);
            setDisplayCardActions(!displayCardActions);
        }

    }

    let styling: React.CSSProperties = {
        backgroundSize: "100% 100%",
        backgroundColor: 'gray',
        padding: "10px",
        margin: "[1,10]",
        ...dimensions
    }

    let cardsLeft = null;

    if (card.meta?.id !== undefined) {
        styling.backgroundImage = `url(${urlProducer(card.meta)})`;
    }
    if (card.meta?.id === 0) {
        styling.backgroundImage = `url(${urlProducer(card.meta)})`;
        cardsLeft = remainingCards;
    }

    let modal: JSX.Element = (<></>);
    if (!isBack && displayCardActions && hasModal === true) {
        modal = (
            <Modal show={displayCardActions} className="card-modal"  onHide={() => setDisplayCardActions(false)} centered={true}>
                    <ModalBody className="card-modal-show" style={{backgroundImage: `url(${urlProducer(card.meta!)})`, backgroundSize: "100% 100%"}}>
                        {!noDisplayActions && <Button className="purchase-action" disabled={!isTurn} onClick={() => buyHandleClick(props)} >Purchase</Button>}
                        {!noDisplayActions && <Button className="reserve-action" disabled={!isTurn} onClick={() => reserveHandleClick(props)}>Reserve</Button>}
                        {!noDisplayActions && !(card.meta?.level==3) && <Button className="take-action" disabled={!isTurn} onClick={() => takeHandleClick(props)}>TAKE</Button>}
                    </ModalBody>
            </Modal>
        )
    }
    return (
        <>
            <Card key={`card-${card.position}-${id}`} style={styling} onClick={handleClick} className="game-card">
                <sup className="remaining-item-info">
                    <Badge bg="secondary">{cardsLeft}</Badge>
                </sup>
            </Card>
            {modal}
        </>

    )
}

export function CardDisplay(props: CardDisplayProps) {
    let dimensions = props.dimension ? props.dimension : SIZE;
    let allCards: GameCard[] = props.cardBack !== false && props.cards.length > 0 ? [{ position: -1, meta: { id: 0, level: props.cards[0].meta?.level, type: props.cards[0].meta?.type}, cardBack: true }] : [];
    let preBuilt: GameCard[];

    if (props.cards.length === 0) {
        preBuilt = Array.apply(null, Array(props.minimumCards)).map((_, index) => emptyCard(index));
    } else {
        preBuilt = allCards.concat(...props.cards);
    }

    let cards = preBuilt
        .sort((aCard, bCard) => aCard.position - bCard.position)
        .reduce((accumulator, curr) => {
            while (accumulator.length < curr.position) {
                accumulator.push(emptyCard(accumulator.length));
            }
            accumulator.push(curr);
            return accumulator;
        }, new Array<GameCard>())
        .map((card, i) => (
            <CardComponent key={`${card.meta?.id}-${card.meta?.level}-${i}`}
                card={card} isBack={card.cardBack} isFree={card.isFree} dimensions={dimensions}
                urlProducer={props.urlProducer} id={props.id} hasActions={true}
                remainingCards={props.remainingCards} updateAction={props.updateAction} isTurn={props.isTurn} hasModal={true} />
        ));
    if (preBuilt.length < props.minimumCards) {
        preBuilt = preBuilt.concat(Array.apply(null, Array(props.minimumCards - preBuilt.length)).map((_, index) => emptyCard(index)))
    }

    if (props.reverse === true) {
        cards = cards.reverse();
    }

    return (
        <div id={props.id} className="card-holder">
            {cards}
        </div>
    )
}
export function CardDisplayCity(props: CardDisplayProps) {
    let dimensions = props.dimension ? props.dimension : SIZE_city;
    let allCards: GameCard[] = [];
    let preBuilt: GameCard[];

    if (props.cards.length === 0 ) {
        preBuilt = Array.apply(null, Array(props.minimumCards)).map((_, index) => emptyCard(index));
    } else {
        preBuilt = allCards.concat(...props.cards);
    }

    preBuilt = preBuilt.slice(0, 3);

    let cards = preBuilt
        .sort((aCard, bCard) => aCard.position - bCard.position)
        .reduce((accumulator, curr) => {
            while (accumulator.length < curr.position) {
                accumulator.push(emptyCard(accumulator.length));
            }
            accumulator.push(curr);
            return accumulator;
        }, new Array<GameCard>())
        .map((card, i) => (
            <CardComponent key={`${card.meta?.id}-${card.meta?.level}-${i}`}
                           card={card} isBack={card.cardBack} isFree = {card.isFree}  dimensions={dimensions}
                           urlProducer={props.urlProducer} id={props.id}  hasActions={true}
                           remainingCards={props.remainingCards} updateAction={props.updateAction} isTurn={props.isTurn}/>));
    if (props.reverse === true) {
        cards = cards.reverse();
    }
    return (
        <div id={props.id} className="card-holder">
            {cards}
        </div>
    )
}
export function CardDisplayTradingPost(props: CardDisplayProps) {
    let dimensions = props.dimension ? props.dimension : SIZE_tradingPost;
    let allCards: GameCard[] = [];
    let preBuilt: GameCard[];

    if (props.cards.length === 0 ) {
        preBuilt = Array.apply(null, Array(props.minimumCards)).map((_, index) => emptyCard(index));
    } else {
        preBuilt = allCards.concat(...props.cards);
    }

    preBuilt = preBuilt.slice(0, 5);

    let cards = preBuilt
        .sort((aCard, bCard) => aCard.position - bCard.position)
        .reduce((accumulator, curr) => {
            while (accumulator.length < curr.position) {
                accumulator.push(emptyCard(accumulator.length));
            }
            accumulator.push(curr);
            return accumulator;
        }, new Array<GameCard>())
        .map((card, i) => (
            <CardComponent key={`${card.meta?.id}-${card.meta?.level}-${i}`}
                           card={card} isBack={card.cardBack} isFree = {card.isFree}  dimensions={dimensions}
                           urlProducer={props.urlProducer} id={props.id}  hasActions={true}
                           remainingCards={props.remainingCards} updateAction={props.updateAction} isTurn={props.isTurn}/>));
    if (props.reverse === true) {
        cards = cards.reverse();
    }
    return (
        <div id={props.id} className="card-holder">
            {cards}
        </div>
    )
}