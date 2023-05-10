import React, { useState } from "react";
import { enumKeys } from "../../../../../../shared/enums";
import { Badge, Card } from "react-bootstrap";
import { getToken } from "../../../constants";
import { GemType } from "../../../../../models/game-types";
import "./owned-gem-display.component.scss";
import { Action } from "../../../../../models/move";
import { Props } from "../shared";




function OwnedGemGenerator(props: Props & {name: string}) {

    let { service, name, actionUpdate, owned, className, isTurn } = props;
    const [isActive, setIsActive] = useState(true);  //TODO: Only activate after purchasing a card.
    const [collectAction, setCollectAction] = useState(false);
    const [burnAction, setBurnAction] = useState(false);

    function handleClick() {
        setCollectAction(!collectAction);
    }
    function burnClick() {
        let action: Action = {
            type: "BURN_TOKEN",
            value: name.toUpperCase()
        }
        actionUpdate(action);
        setCollectAction(!collectAction);
    }
    function payClick() {
        let action: Action = {
            type: "PAYMENT",
            value: name.toUpperCase()
        }
        actionUpdate(action);
        setCollectAction(!collectAction);
    }
    let ownedGems = null;
    if (name === "DIAMOND") ownedGems = owned.DIAMOND;
    if (name === "GOLD") ownedGems = owned.GOLD;
    if (name === "EMERALD") ownedGems = owned.EMERALD;
    if (name === "ONYX") ownedGems = owned.ONYX;
    if (name === "RUBY") ownedGems = owned.RUBY;
    if (name === "SAPPHIRE") ownedGems = owned.SAPPHIRE;

    name = name.toLowerCase();
    let styling: React.CSSProperties = {
        width: 50,
        height: 50,
        borderRadius: "4rem",
        backgroundImage: `url(${getToken(service, name)})`,
        backgroundColor: "black",
        backgroundSize: "100% 100%",
    }
    return (
        <Card className="owned-gem" key={`token-${name}`} style={styling} onClick={handleClick}>
         {collectAction && <button className="child" disabled={!isTurn || !isActive} onClick={payClick}> PAY </button>}
            <sup className="remaining-item-info">
                <Badge bg="primary">
                    {ownedGems}
                </Badge>
            </sup>
          {collectAction && <button className="child2" disabled={!isTurn} onClick={burnClick}> BURN </button>}
        </Card>
    )
}
/**
 * Gem display.  Iterates across all of the GemTypes and display the number of the gem the player has
 * If it is active, then upon click a payment action is added.
 * @param props The props passed to the gems.
 * @returns
 */
export function OwnedGemDisplay(props: Props) {
    return (
        <div className={props.className} id="owned-gem-area">
            {enumKeys(GemType).map((name) => <OwnedGemGenerator
                name={name}
                {...props}
                />)}
        </div>
    )
}