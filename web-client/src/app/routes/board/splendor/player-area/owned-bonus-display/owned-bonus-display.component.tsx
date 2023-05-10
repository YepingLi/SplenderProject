import React, { useState, useEffect } from "react";
import { Gem } from "../../../../../models/game";
import { enumKeys } from "../../../../../../shared/enums";
import { Badge, Card } from "react-bootstrap";
import { getToken } from "../../../constants";
import { Bonus, GemType } from "../../../../../models/game-types";
import "./owned-bonus-display.component.scss";
import { Action } from "../../../../../models/move";
import { Props } from "../shared";
import { useSelector } from "react-redux";
import { gameSelector } from "../../../../../actions/board.actions";


function OwnedBonusGenerator(props: Props & {name: string}) {
    let { name, actionUpdate, isTurn} = props;
    let ownedBonus = null;
    const [burnAction, setBurnAction] = useState(false);
    const [pairAction, setPairAction] = useState(false);
    const gameState = useSelector(gameSelector);
    
    function handleClick() {
        setBurnAction(!burnAction);
        setPairAction(!pairAction);
    }

    function pairClick() {
       let action: Action;

       if(gameState.currentMove && gameState.currentMove.actions.length) {
        let latestAction: Action = gameState.currentMove.actions[gameState.currentMove.actions.length - 1]
        if (latestAction.type === "TAKE_LEVEL_TWO") {
            action = {
                type: "LEVEL_TWO_PAIR",
                value: name.toUpperCase()
            }
        } else if (latestAction.type === "TAKE_LEVEL_ONE") {
            action = {
                type: "LEVEL_ONE_PAIR",
                value: name.toUpperCase()
            }
        } else if (latestAction.type === "BUY_CARD" && latestAction.value.level === 1) {
            action = {
                type: "LEVEL_ONE_PAIR",
                value: name.toUpperCase()
            }
            
        } else if (latestAction.type === "BUY_CARD" && latestAction.value.level === 2) {
            action = {
                type: "LEVEL_TWO_PAIR",
                value: name.toUpperCase()
            }
        } else {
            action = {
                type: "LEVEL_ONE_PAIR",
                value: name.toUpperCase()
            }
        }
       } else {
        action = {
            type: "LEVEL_ONE_PAIR",
            value: name.toUpperCase()
        }
       }
       
       actionUpdate(action);
       setBurnAction(!burnAction);
       setPairAction(!pairAction);
    }

    if (name === "DIAMOND") ownedBonus = props.owned.DIAMOND;
    if (name === "GOLD") ownedBonus = props.owned.GOLD;
    if (name === "EMERALD") ownedBonus = props.owned.EMERALD;
    if (name === "ONYX") ownedBonus = props.owned.ONYX;
    if (name === "RUBY") ownedBonus = props.owned.RUBY;
    if (name === "SAPPHIRE") ownedBonus = props.owned.SAPPHIRE;
    name = name.toLowerCase();
    let styling: React.CSSProperties = {
        width: 50,
        height: 50,
        borderRadius: "4rem",
        backgroundImage: `url(${getToken(props.service, name)})`,
        backgroundColor: "black",
        backgroundSize: "100% 100%",
    }
    return (
        <Card key={`token-${name}`} style={styling} onClick={handleClick}>
            {pairAction && <button className="child2" disabled={!isTurn || ownedBonus == 0} onClick={() => pairClick()}> PAIR </button>}
            <sup className="remaining-item-info">
                <Badge bg="primary">
                    {ownedBonus}
                </Badge>
            </sup>
        </Card>
    )
}
/**
 * Gem display (top down)
 * @param props The props passed to the gems
 * @returns
 */
export function OwnedBonusDisplay(props: Props) {

    return (
        <div className={props.className} id="owned-bonus-area">
            {enumKeys(GemType).map((name) => (
                <OwnedBonusGenerator
                    name={name}
                    {...props}
                />
            ))}
        </div>
    )
}