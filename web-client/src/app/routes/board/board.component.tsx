import React, { useState, useEffect } from "react";
import { Button, Card } from "react-bootstrap"
import "./board.component.scss"
import { useNavigate } from "react-router-dom"
import { useSelector } from "react-redux";
import { gameSelector } from "../../actions/board.actions";
import GameBoard from "../../models/game";
import { PlayerArea } from "./player-area/player-area.component";
import { Orient } from "./orient/orient.component";
import { CardDisplay } from "./card-display/card-display.component";
import { environment } from "../../../environment/environment";
import { GemDisplay } from "./gem-display/gem-display.component";

/**
 * Function tom retrieve the proper expansion
 * 
 * @param game The current game
 * @returns The expansion element
 */
function getExpansion(game: GameBoard) {
    return <Orient game={game}/>;
}


function cardGetter(level: number) {
    return (name: string, type?: string) => {
        return environment.gameAssets.getCardPath(name, level, {type: type})
    }
}

/**
 * The main game node
 * 
 * @param props 
 * @returns 
 */
export function BoardComponent(props: {username: string}) {
    const [counter, setCounter] = useState(20);
    let navigate = useNavigate();
    let gameState = useSelector(gameSelector);
    useEffect(() => {
        if (counter > 0) {
            const interval = setInterval(() => {
              setCounter(counter - 1);
            }, 1000);
            return () => clearInterval(interval);
          }
        }, [counter]);
    return (
        <div id="game-area">
            <div id="headers">
                <div id="left-header" className="header">
                    <Button onClick={() => navigate("/settings")}>Settings</Button>
                    <div>Counter: {counter}</div>
                </div>
                <div id="right-header" className="header">
                    <Button onClick={() => navigate("/sessions")}>Quit Game</Button>
                </div>
            </div>
            <div id="gameboard">
                <header id="header">
                    <CardDisplay id="noble-holder" cards={gameState.game.nobles} urlProducer={environment.gameAssets.getNoblePath} minimumCards={3}/>
                </header>
                <main id="board-main">
                    <div id="normal-cards">
                        <CardDisplay id="normal-lvl-3" cards={gameState.game.nobles} urlProducer={cardGetter(3)} cardBack={true} minimumCards={3}/>
                        <CardDisplay id="normal-lvl-2" cards={gameState.game.nobles} urlProducer={cardGetter(2)} cardBack={true} minimumCards={3}/>
                        <CardDisplay id="normal-lvl-1" cards={gameState.game.nobles} urlProducer={cardGetter(1)} cardBack={true} minimumCards={3}/>
                    </div>
                    <div id="free-gems">
                        <GemDisplay freeGems={gameState.game.freeGems}/>
                    </div>
                    <div id="expansion-area">
                        {getExpansion(gameState.game)}
                    </div>
                    
                </main>
                <PlayerArea username={props.username} game={gameState.game}/>
            </div>
        </div>
    )
}