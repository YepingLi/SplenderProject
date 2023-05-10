import React from "react";
import Game from "../../../../models/game";
import { CardUrlProducer } from "../../../../models/types";
import { OwnedGemDisplay } from "./owned-gem-display/owned-gem-display.component";
import { OwnedBonusDisplay } from "./owned-bonus-display/owned-bonus-display.component";
import { ReservedCardDisplay } from "../../reserved-card-display/reserved-card-display";
import { Action } from "../../../../models/move";
import "./player-area.component.scss";
import { Button, Card } from "react-bootstrap";
import { ReservedNobleDisplay } from "../../reserved-noble-display/reserved-noble-display";

function getNumberOfReservedCards(game: Game, username: string, level: number) {
  let cards = game.players.find(player => player.name === username)?.reservedCards;
  return (cards === undefined || cards === null) ? 0 : cards.filter(card => card.meta?.level === level).length;
}

interface T {
  handle: () => void
  disabled: boolean
}

export interface TurnActions {
  endTurn: T,
  reset: T
}

interface Props {
  username: string,
  game: Game, 
  getCard: (service: string) => CardUrlProducer,
  actionUpdate: (action: Action) => void,
  turnActions: TurnActions,
  isTurn: boolean,
  getNoble: (service: string, id: number) => string
}

export function PlayerArea(props: Props) {

  const { game, username, getCard, actionUpdate, turnActions, getNoble } = props;
  let player = props.game.players.find(p => p.name === username)!;
  return (
    <>
      <Card id="player-div">
        <div> WELCOME {username.toUpperCase()} TO PLAYER AREA!! </div>
        <div className="player-items">
          <div className="item-container flex-4">
            <div style={{ color: "cyan" }}>Reserved Cards</div>
            <ReservedCardDisplay
              id='reserved-card'
              cards={game.players.find(player => player.name === username)!.reservedCards}
              urlProducer={getCard(game.gameServer)}
              minimumCards={0}
              updateAction={actionUpdate}
              isTurn={props.isTurn}
            />
          </div>
          <div className="item-container">
            <div style={{ color: "cyan" }}>Gems</div>
            <OwnedGemDisplay
              owned={player.gems}
              service={props.game.gameServer}
              actionUpdate={actionUpdate}
              className="center-aligned"
              isTurn = {props.isTurn}
            />
          </div>
          <div className="item-container">
            <div style={{ color: "cyan" }}>Bonuses</div>
            <OwnedBonusDisplay
              owned={player.bonuses}
              service={props.game.gameServer}
              actionUpdate={actionUpdate}
              className="center-aligned"
              isTurn = {props.isTurn}
            />
          </div>
          <div className="item-container">
            <div style={{ color: "cyan" }}>Reserved Noble</div>
            <ReservedNobleDisplay
              id='reserved-Noble'
              nobles={game.players.find(player => player.name === username)!.reservedNobles}
              urlProducer={(id) => getNoble(game.gameServer, id)}
              minimumCards={0} 
              isTurn={props.isTurn}
               />
          </div>
          <div className="item-container">
            {game.type === "TRADING_POSTS" && (
              <>
                <div style={{ color: "cyan" }}>Powers</div>
                <ul>
                  {player.powers.map((power, index) => (
                    <li key={index}>{power}</li>
                  ))}
                </ul>
              </>
            )}
          </div>
        </div>
      </Card>
      <div id="turn-actions-container">
        <Button className="action-btn" variant="success"
          onClick={turnActions.endTurn.handle}
          disabled={turnActions.endTurn.disabled}
        >
          END TURN
        </Button>
        <Button className="action-btn"
          variant="warning"
          onClick={turnActions.reset.handle}
          disabled={turnActions.reset.disabled}
        >
          Undo actions!
        </Button>
      </div>
    </>
  );

}