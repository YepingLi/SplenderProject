import React from "react";
import { Gem, GemType } from "../../../models/game";
import { enumKeys } from "../../../../shared/enums";
import { Card } from "react-bootstrap";
import { environment } from "../../../../environment/environment";

/**
 * Gem display (top down)
 * @param props The props passed to the gems
 * @returns 
 */
export function GemDisplay(props: {freeGems: Gem[]}) {
    function gemGenerator(name: string) {
        name = name.toLocaleLowerCase();
        let styling: React.CSSProperties = {
            width: 40,
            height: 40,
            borderRadius: "1rem",
            backgroundImage: `url(${environment.gameAssets.getTokenPath(name)})`,
            backgroundColor: "black",
            backgroundSize: "contain",
        }
        
        return (
            <Card key={`token-${name}`} style={styling}/>
        )
    }

    return (
        <div>
            {enumKeys(GemType).map(gemGenerator)}
        </div>
    )
}