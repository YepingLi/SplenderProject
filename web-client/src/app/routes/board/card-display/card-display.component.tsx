import React from "react";
import { CardDimension, GameCard, emptyCard } from "../../../models/game";
import { Card } from "react-bootstrap";
import "./card-display.component.scss";

const SIZE: CardDimension = {height: 100, width: 50};
type UrlProducer = (name: string) => string;
type CardDisplayProps = {
    id: string, 
    cards: GameCard[], 
    urlProducer: UrlProducer, 
    dimension?: CardDimension, 
    cardBack?: boolean, 
    minimumCards: number
}

function cardGenerator(card: GameCard, dimensions: CardDimension, urlProducer: UrlProducer, id: string, props: CardDisplayProps) {    
    let styling: React.CSSProperties = { 
        backgroundSize: "contain",
        backgroundColor: 'gray',
        ...dimensions
    }
    
    if(card.position == 0){
        styling.backgroundImage = `url(${urlProducer("CardBack")})`;
    }else if (card.name) {
        styling.backgroundImage = `url(${urlProducer(card.name)})`;
    }
    return <Card key={`card-${card.position}-${id}`} style={styling} className="game-card"/>
}

export function CardDisplay(props: CardDisplayProps) {
    let dimensions = props.dimension ? props.dimension : SIZE; 
    let allCards: GameCard[] = props.cardBack === true && props.cards.length > 0 ? [{position: -1, name: "CardBack"}]:[]; 
    let preBuilt: GameCard[];

    if (allCards.length === 0) {
        let addOne = props.cardBack === true ? 1:0;
        preBuilt = Array.apply(null, Array(props.minimumCards + addOne)).map((_, index) => emptyCard(index));
    } else {
        preBuilt = allCards.concat(...allCards.map((card) => {
            return {
                position: card.position + 1,
                name: card.name
            }
        }));
    }

    let cards = preBuilt
        .sort((aCard, bCard) => Number(aCard.position > bCard.position))
        .reduce((accumulator, curr) => {
            while (accumulator.length < curr.position) {
                accumulator.push(emptyCard(accumulator.length));
            }
            accumulator.push(curr);
            return accumulator;
        }, new Array<GameCard>())
        .map((card) => cardGenerator(card, dimensions, props.urlProducer, props.id, props));
    if (preBuilt.length < props.minimumCards) {
        preBuilt = preBuilt.concat(Array.apply(null, Array(props.minimumCards - preBuilt.length)).map((_, index) => emptyCard(index)))
    }

    return (
        <div id={props.id} className="card-holder">
            {cards}
        </div>
    )
}