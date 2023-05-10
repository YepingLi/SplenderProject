import React, { Component } from "react";
import Player from "../../models/player";
import { User } from "../../models/user";
interface Props{
    user : Player
}
export function EndGame(props:Props){
  const handleClick = () => {
  };

  return (
   <div className="modal">
     <div className="modal_content">
     <span className="close" onClick={handleClick}>&times;    </span>
     <p>{props.user.name} just won!!!!</p>
     <a href="/sessions">click to return to sessions page</a>
    </div>
   </div>
  );

}