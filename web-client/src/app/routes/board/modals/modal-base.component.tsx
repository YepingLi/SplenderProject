import React from "react";
import { Modal } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { ValidMove, resetMove } from "../../../actions/board.actions";
import { Action } from "../../../models/move";
import { addToast } from "../../../actions/toast.actions";

type BaseProps = {
    onSuccess: (action: Action) => void,
    onClose: () => void,
}


export type Props = BaseProps & {
    service: string
}

export function BaseModal(props: BaseProps & {name: string, inner: JSX.Element}) {
    const dispatch = useDispatch();

    function createToast(msg: string) {
        dispatch(addToast({ msg: msg, source: props.name}))
    }

    return (
        <Modal aria-labelledby="contained-modal-title-vcenter"
            centered
            show={true}
            onHide={() => {
                dispatch(resetMove());
                createToast("Move was reset as no payment was chosen!");
                props.onClose();
            }}
        >
            {props.inner}
        </Modal>
    )
}