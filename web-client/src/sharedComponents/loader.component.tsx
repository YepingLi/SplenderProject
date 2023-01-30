import React, { useEffect, useState } from "react";
import { Spinner } from "react-bootstrap";

function PeriodAdder(props: {max: number, text: string}) {
    const [numDots, setNumDots] = useState(0);
    function periodAdderReducer() {
        let next = (numDots + 1) % props.max;
        setNumDots(next);
    }

    useEffect(() => {
        let functId = setTimeout(periodAdderReducer, 250);
        return () => {
            clearTimeout(functId);
        }
    }, [numDots])
    
    let periods = Array.apply(null, Array(numDots + 1)).map(() => "").join(".");
    return (
        <div style={{fontSize: "2rem"}}>
            {props.text}{periods}
        </div>
    );
}

export default function LoaderComponent() {
    const style: React.CSSProperties = {
        display: "flex",
        width: "100%",
        height: "100%",
        alignItems: "center",
        justifyContent: "center",
        flexDirection: "column"
    };
    
    return (
        <div style={style}>
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
            <PeriodAdder max={5} text="Loading"/>
        </div>
        
    )
}
