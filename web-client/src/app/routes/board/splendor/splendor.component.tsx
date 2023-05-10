import { SplendorProps } from "../../../models/boardProps";
import { CardDisplay } from "../card-display/card-display.component"
import { NobleDisplay } from "../noble-display/noble-display.component"
import { GemDisplay } from "./gem-display/gem-display.component";
import { PlayerArea } from "./player-area/player-area.component";
import "./splendor.component.scss"
import Leaderboard from './leaderboard/leaderboard.component';

export function Splendor(props: SplendorProps) {
    let { 
        game, 
        getNoble,
        getCard, 
        service,
        username,
        inBoard,
        updateActions,
        header,
        turnActions,
        isTurn,
    } = props;

    const board = game.board;
    
    let boardWidth = inBoard !== undefined ? 60 : 100;
    return (
        <div id="splendor">
            <div id="board-area-main">
                <div id="playable-area">
                    <header id="header">
                        <div style={{display: "flex", justifyContent: "space-between"}}>
                            <div  id='nobleDisplay' className='noble-display'>
                                <NobleDisplay id="noble-holder" nobles={board.nobles}
                                    urlProducer={(id) => getNoble(service, id)} minimumCards={game.players.length + 1}
                                    updateAction={updateActions} isTurn = {props.isTurn}/>
                            </div>
                            {header?header : <></>}
                        </div>
                    </header>
                    <main id="board-main">
                        <div id='basic-board' style={{width: `${boardWidth}%`}}>
                            <div id="normal-cards">
                                <CardDisplay id="normal-lvl-3" 
                                    cards={board.faceUpCards.filter(obj => obj.meta?.level === 3)}
                                    urlProducer={getCard(service)} minimumCards={4} 
                                    remainingCards={board.remainingLevelThreeCards}
                                    updateAction={updateActions} isTurn = {props.isTurn}/>
                                <CardDisplay id="normal-lvl-2" 
                                    cards={board.faceUpCards.filter(obj => obj.meta?.level === 2)}
                                    urlProducer={getCard(service)} minimumCards={4} 
                                    remainingCards={board.remainingLevelTwoCards}
                                     updateAction={updateActions}  isTurn = {props.isTurn}/>
                                <CardDisplay id="normal-lvl-1" 
                                    cards={board.faceUpCards.filter(obj => obj.meta?.level === 1)}
                                    urlProducer={getCard(service)} minimumCards={4} 
                                    remainingCards={board.remainingLevelOneCards}
                                     updateAction={updateActions}  isTurn = {props.isTurn}/>
                            </div>
                            <div id="free-gems">
                                <GemDisplay service={service} gemMap={board.availableGems} updateAction={updateActions} isTurn = {props.isTurn}/>
                            </div>
                        </div>
                        <div id="inner-board-component" style={{width: `${100-boardWidth}%`}}>
                            {props.inBoard}
                        </div>
                    </main>
                </div>
                <div id="leaderboard">
                     <Leaderboard players={game.players} curPlayer = {game.curPlayer} service={props.service}/>
                </div>
            </div>
            <footer style={{display: "flex"}}>
                <PlayerArea turnActions={turnActions} 
                    username={username} 
                    game={game} 
                    getCard={getCard}
                    actionUpdate={updateActions}
                    isTurn={props.isTurn}
                    getNoble={getNoble}/>
            </footer>
        </div>
    )
}