import React from "react";
import { Modal, ModalBody, Card } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { ValidMove, findMoves, gameSelector } from "../../../actions/board.actions";
import { addToast } from "../../../actions/toast.actions";
import { getNoble } from "../constants";
import { BaseModal, Props } from "./modal-base.component";

type Payment = {
    numDoubleGold: number,
    gems: { [s: string]: number }
}

export function ClaimNobleModal(props: Props) {
    const name = "Claim Noble";
    const dispatch = useDispatch();
    const gameState = useSelector(gameSelector);

    function createToast(msg: string) {
        dispatch(addToast({ msg: msg, source: name }))
    }

    const nobles = findMoves(gameState.moves, gameState.currentMove!).flatMap(move => move.actions.map(action => {
        return { action: action, id: move.actionId }
    })).filter(x => x.action.type === "CLAIM_NOBLE")

    let modalInner = (
        <ModalBody>
            <Modal.Title>
                Claim Noble
            </Modal.Title>
            <Modal.Body style={{ display: "flex", flexWrap: "wrap" }}>
                {
                    nobles.map(noble => (
                        <Card onClick={() => props.onSuccess(noble.action)}
                            style={{
                                backgroundImage: `url(${getNoble(props.service, noble.action.value)})`,
                                height: "10rem",
                                width: "8.7rem",
                                backgroundSize: "100% 100%"
                            }} />
                    ))
                }
            </Modal.Body>
        </ModalBody>
    )

    return (
        <BaseModal {...props} inner={modalInner} name={name} />
    )
}