import React, { useState, useEffect } from "react";
import { Button } from "react-bootstrap"
import "./board.component.scss"
import { useNavigate, useParams } from "react-router-dom"
import Game, { Meta } from "../../models/game";
import { Orient } from "./orient/orient.component";
import { getCard, getNoble, getBackground } from "./constants";
import Session from "../../models/sessions";
import { LobbyService } from "../../services/lobby.service";
import { LoaderComponent } from "../../../sharedComponents";
import { GameService } from "../../services/game.service";
import { Splendor } from "./splendor/splendor.component";
import { TradingPosts } from "./tradingpost/tradingpost.component";
import { useDispatch, useSelector } from "react-redux";
import { ResponseError } from "../../../shared/httpClient";
import { Action, Move } from "../../models/move";
import { addToast } from "../../actions/toast.actions";
import { ValidMove, gameSelector, isPossibleMove, resetMove, updateGameState, updateMove } from "../../actions/board.actions";
import { CitiesOrient } from "./city/city.component";
import { TurnActions } from "./splendor/player-area/player-area.component";
import { PaymentModal } from "./modals/payment-modal.component";
import { BoardModalType } from "../../models/render-types";
import { ClaimNobleModal } from "./modals/claim-noble.component";
import { EndGame } from "./endgamepopup.component";
//import { OwnedGemDisplay } from '../../splendor/owned-gem-display';



const gameService = new GameService();


/**
 * Get the from the server and serve it to the page
 * 
 * @param service 
 * @param level 
 * @returns 
 */
function cardGetter(service: string) {
    return (meta: Meta) => {
        return getCard(service, meta);
    }
}

/**
 * Function tom retrieve the proper expansion
 * 
 * @param game The current game
 * @returns The expansion element
 */
function getGameBoard(game: Game, nobleProducer: (server: string, id: number) => string, username: string, service: string, upadateActions: (action: Action) => void, turnActions: TurnActions) {
    if (game.isOver) {
        addToast({ msg: "GAME OVER!!!!", source: "Game" });
    }

    let isTurn = false;

    if (game.curPlayer.name === username) {
        isTurn = true;
    }
    const getComponent = () => {
        switch (game.type) {
            case "BASE":
                return Splendor
            case "ORIENT":
                return Orient;
            case "TRADING_POSTS":
                return TradingPosts
            case "CITIES_ORIENT":
                return CitiesOrient;
            default:
                throw new Error("Not handled!");
        }
    }
    let Component = getComponent();
    return <Component
        game={game}
        getCard={cardGetter}
        getNoble={nobleProducer}
        service={service}
        username={username}
        updateActions={upadateActions}
        isTurn={isTurn}
        isLevelOne={false}
        turnActions={turnActions} />
}

/**
 * The main game component
 * 
 * @param props 
 * @returns 
 */
export function BoardComponent(props: { username: string }) {
    const dispatch = useDispatch();
    const [counter, setCounter] = useState(100);
    const [session, setSession] = useState<Session | null>(null);
    const [longPoll, setLongPoll] = useState({ poll: true, counter: 0 });
    let navigate = useNavigate();
    let params = useParams<{ id: string }>();
    const [isFinalized, setIsFinalized] = useState(false);
    const [actionId, setActionId] = useState(0);
    const [modal, setModal] = useState<{show: boolean, type?: BoardModalType}>({show: false});
    const gameState = useSelector(gameSelector);

    function handleEndTurn() {
        console.log("Sending the players turn.");
        gameService.putMove(session!, actionId);
    }

    function handleResetEvent() {
        console.log("Reset the players actions");
        dispatch(resetMove());
    }

    //Checks if it is the current players turn or not
    function isTurn() {
        if (gameState.game === undefined) {
            return false;
        }
        if (gameState.game.curPlayer.name === props.username) {
            return true;
        } else {
            return false;
        }
    }

    function createToast(msg: string) {
        dispatch(addToast({ msg: msg, source: "Board" }))
    }
    //A similar helper function which tells you if an action type is in a move.
    function containedInMove(move: Move, type: string) {
        for (let i = 0; i < move.actions.length; i++) {
            if (i >= move.actions.length) {
                continue;
            }
            if (move.actions[i].type === type) {
                return true;
            }
        }
        return false;
    }

    function handleSaveGame(){
        gameService.saveGame(session!)
    }
    function updateActionsHook(action: Action) {
        try {
            let res = isPossibleMove(gameState, action);
            dispatch(updateMove(action))
            createToast(res.msg);
            if (!res.requiresNext) {
                setIsFinalized(true);
                setActionId(res.actionId!);
                setModal({show: false})
            } else if (res.modal !== undefined) {
                setModal({show: true, type: res.modal});
            } else if (res.requiresNext && res.modal === undefined) {
                // NEED TO LOOK AT THE BOARD!
                setModal({show: false});
            }
        } catch (err: any) {
            createToast(err.message);
        }
    }

    useEffect(() => {
        if (counter > 0) {
            const interval = setInterval(() => {
                setCounter(counter - 1);
            }, 1000);
            return () => clearInterval(interval);
        }
    }, [counter]);

    useEffect(() => {
        let abort = new AbortController();
        if (longPoll.poll !== true) {
            return;
        }

        if (session === null || gameState.hashableGame === undefined) {
            return;
        }
        gameService
            .getGame(session, gameState.hashableGame!.hash, abort).then(game => {
                console.log(game.curPlayer, props.username);
                let gameP = Promise.resolve(game);
                if (game.curPlayer.name !== props.username) {
                    return Promise.all([gameP, Promise.resolve([])]);
                }
                return Promise.all([gameP, gameService.getMoves(session)])
            }).then(([game, moves]) => {
                dispatch(updateGameState({ game: game, moves: moves }));
                setLongPoll((v) => {
                    return { ...v, counter: v.counter + 1 }
                });
            })
            .catch((err: ResponseError) => {
                if (err.resp.status > 399 && (err.resp.status === 408 || err.resp.status !== 503)) {
                    return;
                }
                console.log("handling failure");
                setLongPoll((v) => {
                    return { ...v, counter: v.counter + 1 }
                });
            });
        return () => {
            abort.abort();
        }
    }, [longPoll]);

    useEffect(() => {
        if (gameState.game !== undefined) {
            createToast("It is now " + gameState.game.curPlayer.name + "'s turn")
        }
    }, [gameState.game?.curPlayer.name]);

    useEffect(() => {
        let aborter = new AbortController();
        new LobbyService()
            .getSession(params.id!)
            .then(session => {
                setSession(session);
                return Promise.all([gameService.getGame(session, undefined, aborter), Promise.resolve(session)])
            })
            .then(([_game, session]) => {
                if (_game.curPlayer.name === props.username) {
                    return Promise.all([Promise.resolve(_game), gameService.getMoves(session)])
                }
                return Promise.all([Promise.resolve(_game), Promise.resolve([])])
            }).then(([game, moves]) => {
                dispatch(updateGameState({ game: game, moves: moves }));
                setLongPoll(() => {
                    return { poll: true, counter: 1 }
                });
            })
            .catch((err: Error) => {
                if (err instanceof DOMException) {
                    return;
                }
                navigate("/sessions");
            });
        return () => {
            aborter.abort();
        }
    }, []);

    /**
     * If the session is null, wait...
     */
    if (session === null || gameState.game === undefined) {
        return (
            <LoaderComponent />
        )
    }

    const turnActions: TurnActions = {
        reset: {
            handle: handleResetEvent,
            disabled: !isTurn() || gameState.currentMove === undefined || gameState.currentMove?.actions.length === 0
        },
        endTurn: {
            handle: handleEndTurn,
            disabled: !isFinalized || !isTurn() || gameState.currentMove === undefined || gameState.currentMove.actions.length === 0
        }
    }
    let service = session.gameParameters.location;

    function getModal() {
        switch (modal.type) {
            case "PICK_PAYMENT":
                return <PaymentModal service={service} onSuccess={updateActionsHook} onClose={() => setModal({show: false})} />
            case "CLAIM_NOBLE":
                return <ClaimNobleModal service={service} onSuccess={updateActionsHook} onClose={() => setModal({show: false})} />
            default:
                return (<></>)
        }
    }

    return (
        <div id="game-area" style={{ backgroundImage: `url(${getBackground(service)})` }}>
            <div>{isFinalized? <EndGame user={gameState.game.curPlayer}></EndGame>:null}</div>
            <div id="headers">
                <div id="left-header" className="header">
                    <Button onClick={() => navigate("/settings")}>Settings</Button>
                    <div>Counter: {counter}</div>
                </div>
                <div id="right-header" className="header">
                    <Button onClick={() => navigate("/sessions")}>Quit Game</Button>
                    <Button disabled={session === null} onClick={()=> handleSaveGame()}>Save Game</Button>
                </div>
            </div>
            <div>{isFinalized?<EndGame user={gameState.game.curPlayer}></EndGame>:null}</div>
            {getGameBoard(gameState.game, getNoble, props.username, service, updateActionsHook, turnActions)}
            {
                modal.show && getModal()
            }
        </div>
    )
}