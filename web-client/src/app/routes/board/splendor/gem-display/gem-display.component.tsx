import React, { useState } from "react";
import { enumKeys } from "../../../../../shared/enums";
import { Badge, Button, Card, Modal, ModalBody } from "react-bootstrap";
import { getToken } from "../../constants";
import { GemType, GemTypeString } from "../../../../models/game-types";
import "./gem-display.component.scss";
import { Action } from "../../../../models/move";


function GemRender(props: {
    name: string,
    service: string,
    remainingGems: number,
    actionUpdate: (action: Action) => void,
    isTurn: boolean
}) {

    let { name, remainingGems, isTurn } = props;
    const [collectAction, setCollectAction] = useState(false);

    function handleClick() {
        setCollectAction(!collectAction);
    }

    function burnClick(){
        let action:Action = {
             type:  "BURN_TOKEN",
             value: name.toUpperCase()
              }
              props.actionUpdate(action);
              setCollectAction(!collectAction);
          }
    function collectClick() {
        let action: Action = {
            type: "TAKE_TOKEN",
            value: name.toUpperCase()
        }
        props.actionUpdate(action);
        setCollectAction(!collectAction);
    }

    let url =  `url(${getToken(props.service, name)})`;
    name = name.toLocaleLowerCase();
    let styling: React.CSSProperties = {
        width: 40,
        height: 40,
        borderRadius: "4rem",
        backgroundImage: url,
        backgroundColor: "black",
        backgroundSize: "100% 100%",
        margin: "auto",
    }

    let modal = (
        <Modal show={collectAction} className="gem-modal"  onHide={() => setCollectAction(false)} centered={true}>
            <ModalBody className="gem-modal-show" style={{backgroundImage: url, backgroundSize: "100% 100%"}}>
                {<Button className="purchase-action" disabled={!isTurn} onClick={() => {
                    collectClick()
                    setCollectAction(false);
                }}>Collect</Button>}
            </ModalBody>
        </Modal>
    )
    return (
        <>
        <Card key={`token-${name}`} style={styling} onClick={handleClick}>
            <sup className="remaining-item-info">
                <Badge bg="secondary">{remainingGems}</Badge>
            </sup>
        </Card>
        {collectAction && modal}
        </>
    )
}

/**
 * Gem display (top down)
 * @param props The props passed to the gems
 * @returns 
 */
export function GemDisplay(props: { service: string, gemMap: Record<GemTypeString, number>, updateAction: (action: Action) => void, isTurn: boolean }) {
    return (
        <div id="gem-area">
            {enumKeys(GemType).map((name) => <GemRender name={name} service={props.service} remainingGems={props.gemMap[name]} actionUpdate={props.updateAction} isTurn={props.isTurn} />)}
        </div>
    )
}