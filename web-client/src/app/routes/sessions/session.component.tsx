import { ICellRendererParams, ValueFormatterParams } from 'ag-grid-community';
import { AgGridReact } from 'ag-grid-react';
//import { useHistory } from "react-router-dom";
import { Link, useNavigate } from 'react-router-dom';
import React, { useRef, useState, useEffect, RefObject } from 'react';
import { Button, Form, InputGroup, Modal } from 'react-bootstrap';
import Session from '../../models/sessions';
import { useDispatch, useSelector } from 'react-redux';
import { sessionSelector, updateSessions } from '../../actions/sessions.actions';
import 'ag-grid-community/styles/ag-grid.css'; // Core grid CSS, always needed
import 'ag-grid-community/styles/ag-theme-alpine.css'; // Optional theme CSS
import './session.component.scss'
import { LobbyService } from '../../services/lobby.service';
import { gameServiceSelector, updateGameServices } from '../../actions/game-services.actions';
import { MinimalGameService } from '../../models/gameService';
import { addToast } from '../../actions/toast.actions';
import { ResponseError } from '../../../shared/httpClient';

const sessionService = new LobbyService();

/**
 * Functional Session component
 * @param props The props passed to the component
 * @returns
 */
export function SessionsComponent(props: { username: string }) {
    let gridRef: RefObject<AgGridReact> = useRef(null);
    let sessionData = useSelector(sessionSelector).sessions;
    const dispatch = useDispatch();
    const navigate = useNavigate();
    // Setting up the objects
    const [showing, setShowing] = useState(false);
    const [gameServers, setGameServices] =  useState<MinimalGameService[]>([]);
    const [selected, setSelected] = useState(0);
    const [saveGameId, setSaveGameId] = useState("");
    const [sessionLongPoll, setSessionPoll] = useState(0);
    let isDisabled = gameServers.length === 0;

    // Hook to get data on component mount. We retreive all data at this point.
    useEffect(() => {
        let aborter = new AbortController();
        if (!showing)
            return;
        sessionService.getGameServices(aborter).then((services) => setGameServices(services));
        return () => {
            aborter.abort();
        }
    }, [showing]);

    /**
     * Function to handle the long polling of the server
     * @returns
     */
    const longPolling = () => {
        let updateCounter = () => {
            setSessionPoll((c) =>  c+1);
        }
        let aborter: AbortController = new AbortController();
        sessionService
            .pollSession(sessionData, aborter)
            .then((sessions) => {
                console.log("Received");
                updateCounter();
                dispatch(updateSessions(sessions));
            })
            .catch((err: ResponseError) => {
                console.log(err);
                if (err.resp === undefined) {
                    return;
                }
                if (err.resp.status >= 400 && err.resp.status !== 408) {
                    // Try again in 5 seconds
                    window.location.href = "/";
                    return;
                }
                updateCounter();
            });
        return () => {
            aborter.abort();
        }
    }

    useEffect(longPolling, [sessionLongPoll]);

    function createToast(msg: string) {
        dispatch(addToast({msg: msg, source: "Sessions"}))
    }


    /**
     * Renders all possible actions into the action area of the grid
     * @param param The data param (Session)
     * @param user the user of the GUI
     * @returns The proper components
     */
    function renderActions(param: Session, user: string) {
        if (param.creator === user && !param.launched) {
            return (
                <>
                    <Button onClick={() => handleDelete(param.id)}>Delete</Button>
                    <Button onClick={() => handleLaunch(param)} disabled={param.players.length < param.gameParameters.minSessionPlayers || param.players.length > param.gameParameters.maxSessionPlayers}>
                        Launch
                    </Button>
                    <Link to={`/admin/${param.id}`}>
                        <Button>Admin</Button>
                    </Link>
                </>
            )
        } else if (param.players.includes(user) && param.launched) {
            return (<Button onClick={() =>{
                window.location.href = `/board/${param.id}`;
            }}>Play</Button>)
        }
        else if (param.players.length < param.gameParameters.maxSessionPlayers && !param.players.includes(user)) {
            return (<Button onClick={() => handleJoinEvent(param.id, user)}>Join</Button>)
        } else if (param.players.includes(user)) {
            return (<Button onClick={() =>  handleLeaveEvent(param.id, user)}>Leave</Button>);
        }
        return (
            <Button disabled={true}>No actions</Button>
        )
    }

    function handleJoinEvent(sessionId: string, username: string) {
        sessionService.joinSession(sessionId, username)
            .catch(() => createToast(`Failed to perform action join on session ${sessionId}`))
    }

    function handleLeaveEvent(sessionId: string, username: string) {
        sessionService.leaveSession(sessionId, username)
            .catch(() => createToast(`Failed to perform action leave on session ${sessionId}`))
    }
    /**
     * Handle the click of the create button.
     */
    function handleCreateEvent() {
        if (isDisabled) {
            return setShowing(false);
        }
        setShowing(false);
        sessionService.createSession(props.username, gameServers[selected].name, saveGameId)
            .then((sessionId) => {
                createToast("Successfully created new session!")
                console.log("sessionid", sessionId);
                return sessionService.getSession(sessionId);
            }).catch(err => err.resp.text().then((t:string)=>createToast(t)) );
    }
    function handleDelete(sessionId: string) {
        sessionService.deleteSession(sessionId) //TODO:
            .then(() => createToast(`Successfully deleted session ${sessionId}`))
            .catch(() => createToast(`Failed to delete session with id ${sessionId}`));
    }


    //Session is the session id and the token is the user's token.
    function handleLaunch(session: Session) {
        //Sends the request to LS to launch the session
        sessionService.launchSession(session.id)
            .catch(() => createToast(`Failed to perform action leave on session ${session.id}`))
    }

    let options = gameServers.map((server, index) => {
        return (
            <option value={index} key={`server-option-key-${index}`}>
                {server.name}
            </option>
        )
    })


    // The grid options
    const gridOptions = {
        columnDefs: [
            {
                field: "creator",
                headerName: "Creator"
            },
            {
                field: "players",
                headerName: "Players",
                valueFormatter: (params: ValueFormatterParams) => params.value.join(", ")
            },
            {
                field: "savegameid",
                headerName: "Game Id"
            },
            {
                field: "id",
                headerName: "Session ID"
            },
            {
                headerName: "Actions",
                cellRenderer: (param: ICellRendererParams) => renderActions(param.data, props.username)
            },
        ]
    }

    return (
        <div id="sessionTab">
            <div id="session-header">
                <h3>Available Sessions</h3>
                <div id="session-actions">
                    <Button variant='success' onClick={() => setShowing(true)}>Create Session</Button>
                </div>
            </div>
            <div className="agBox">
                <AgGridReact
                    className="ag-theme-alpine"
                    gridOptions={gridOptions}
                    defaultColDef={{flex: 1}}
                    ref={gridRef}
                    rowData={sessionData} />
            </div>
            <Modal
                aria-labelledby="contained-modal-title-vcenter"
                centered
                show={showing} onHide={() => setShowing(false)}
            >
                <Modal.Header closeButton={true}>
                    <Modal.Title>Create a new session!</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>Select a game service to start the game on</p>
                    <InputGroup>
                        <InputGroup.Text>Game servers:</InputGroup.Text>
                        <Form.Select disabled={isDisabled} defaultValue={selected} onChange={(event) => setSelected(Number(event.target.value))}>
                            {!isDisabled ? options : (<option>No servers available!</option>)}
                        </Form.Select>
                    </InputGroup>
                    <InputGroup>
                        <InputGroup.Text>Save Game Id:</InputGroup.Text>
                        <Form.Control type="text" placeholder='Enter save game Id' onChange={(event) => setSaveGameId(event.target.value)} />
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button disabled={isDisabled} onClick={handleCreateEvent}>Start new session</Button>
                    <Button onClick={() => setShowing(false)}>Delete</Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}