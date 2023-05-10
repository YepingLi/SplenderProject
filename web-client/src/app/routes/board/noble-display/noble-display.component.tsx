import React, { useState } from "react";
import { CardDimension, GameCard, emptyCard } from "../../../models/game";
import { Button, Card, Modal, ModalBody } from "react-bootstrap";
import { UrlProducer } from "../../../models/types";
import "./noble-display.component.scss";
import { Action } from "../../../models/move";


const SIZE: CardDimension = { height: 120, width: 120 };
type NobleDisplayProps = {
    id: string,
    nobles: GameCard[],
    urlProducer: UrlProducer,
    dimension?: CardDimension,
    minimumCards: number
    updateAction?: (action: Action) => void,
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
    updateAction?: (action: Action) => void,
    isTurn: boolean
}

function NobleComponent(props: NobleGeneratorProps) {

    const [displayCardActions, setDisplayCardActions] = useState(false); //TODO:
    const { card, dimensions, urlProducer, id, isNoble, isBack, remainingCards, hasActions, isTurn } = props;
    const noDisplayActions = hasActions === false;

    //TODO: Only display the options if the player is eligible.
    function handleClick() {
        setDisplayCardActions(!displayCardActions);
    }
    function claimHandleClick(props: NobleGeneratorProps) {
        let action: Action = {
            type: "CLAIM_NOBLE",
            value: props.card.meta!
        }
        props.updateAction!(action);
        setDisplayCardActions(!displayCardActions);
    }
    function reserveHandleClick(props: NobleGeneratorProps) {
        let action: Action = {
            type: "RESERVE_NOBLE",
            value: props.card.meta!.id
        }
        props.updateAction!(action);
        setDisplayCardActions(!displayCardActions);
    }

    let styling: React.CSSProperties = {
        backgroundSize: "100% 100%",
        backgroundColor: 'gray',
        padding: "5px",
        margin: "[1,10]",
        height: dimensions.height,
        width: dimensions.width,
    }

    let cardsLeft = null;

    if (card.meta?.id !== undefined) {
        styling.backgroundImage = `url(${urlProducer(card.meta.id)})`;
    }
    return (
        <>
            <Card key={`card-noble-${card.position}-${id}`} style={styling} onClick={handleClick} className="noble-card" />
            {
               (
                    <Modal show={displayCardActions} className="noble-card-modal" onHide={() => setDisplayCardActions(false)} centered={true}>
                        <ModalBody className="noble-card-modal-show" style={{ backgroundSize: "100% 100%", backgroundImage: `url(${urlProducer(card.meta!.id!)})` }}>
                            {!noDisplayActions && <Button className="claim-action" disabled={!isTurn} onClick={() => claimHandleClick(props)} >Claim</Button>}
                            {!noDisplayActions && <Button className="reserve-action" disabled={!isTurn} onClick={() => reserveHandleClick(props)}>Reserve</Button>}
                        </ModalBody>
                    </Modal>
                )
            }
        </>

    )
}

export function NobleDisplay(props: NobleDisplayProps) {
    let dimensions = props.dimension ? props.dimension : SIZE;
    let preBuilt: GameCard[] = [];

    props.nobles.forEach(noble => {
        if (noble.meta) {
            preBuilt.push(noble);
        }
    });

    let cards = preBuilt
        .map((card, i) => (
            <NobleComponent key={`${card.meta?.id}-${card.meta?.level}-${i}`}
                card={card} isBack={card.cardBack} isFree={card.isFree} dimensions={dimensions}
                urlProducer={props.urlProducer} id={props.id} hasActions={true}
                updateAction={props.updateAction} isTurn={props.isTurn} />
        ));

    return (
        <div id={props.id} className="card-holder">
            {cards}
        </div>
    )
}
