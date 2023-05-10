import React, { useState } from 'react';
import Player from "../../../../models/player";
import { Badge, Button, Card, Modal } from 'react-bootstrap';
import { getToken } from '../../constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import './leaderboard.component.scss';
import { faStar } from '@fortawesome/free-solid-svg-icons';

interface Props {
  players: Player[];
  curPlayer: Player;
  service: string
}

const Leaderboard: React.FC<Props> = ({service, players, curPlayer }) => {
  const sortedPlayers = [...players].sort((a, b) => b.playerPrestige - a.playerPrestige);
  const [modalData, setModalData] = useState<{player?: Player, showing: boolean}>({showing: false});
  function getModalInner() {
      if (!modalData.showing) {
        return;
      }

      return (
      <Card className="grow-entry">
        <h3>{modalData.player?.name}</h3>
        <br></br>
        <div>
          <h4>Points: {modalData.player?.playerPrestige}</h4>
        </div>
        <div>
          <h4>Gems:</h4>
          <div className={"gem-box"}>
          {
            Object.entries(modalData.player!.gems).map(([gemName, num]) =>{
              return (
                <Card key={`token-${gemName}`} 
                  className="leader-tokens" 
                  style={{backgroundImage: `url(${getToken(service, gemName)})`}}>
                    <sup style={{display: "flex", justifyContent: "flex-end"}}>
                      <Badge style={{right: 0}} bg={num > 0?"primary":"secondary"}>{num}</Badge>
                    </sup>
                </Card>
              )
            })
          }
          </div>
         </div>
          <div>
            <h4>Bonuses:</h4>
            <div className={"gem-box"}>
            {
                Object.entries(modalData.player!.bonuses)
                .filter(([bonusName, num]) => bonusName !== "NONE")
                .map(([bonusName, num]) =>{
                return (
                    <Card key={`gem-${bonusName}`}
                     className="leader-tokens"
                     style={{backgroundImage: `url(${getToken(service, bonusName)})`}}>
                        <sup style={{display: "flex", justifyContent: "flex-end"}}>
                        <Badge style={{right: 0}} bg={num > 0?"primary":"secondary"}>{num}</Badge>
                        </sup>
                    </Card>
                 )
                })
            }
          </div>
        </div>
        <div>
            <h4>Powers:</h4>
            <div>
                 {modalData.player?.powers.map((power, index) => (
                 <div key={`power-${index}`} className="power-entry">
                   {power}
                 </div>
                     ))}
            </div>
        </div>
      </Card>
    )
      
  }

  return (
    <>
      <Card id="leaderboard-inner">
        <h2>Leaderboard</h2>
        {sortedPlayers.map((player, index) =>  (
            <Card className="leaderboard-entry" key={`${player}-${index}`} onClick={() => setModalData({player: player, showing: true})} >
              <div key={`player-${index}-entry`} className="grow-entry">
                <div key={`player-${index}-colour-circle`} className="color-entry" style={{background: player.colour}}></div>
                <div className='name-point-box' key={`player-${index}-box`}>
                  <div key={`player-${index}-name`} className="player-name" style={{color: player.name === curPlayer.name ? 'lime': undefined}}>{player.name}</div>
                  <div className="point-entry" key={`player-${index}-points`}>
                    <FontAwesomeIcon icon={faStar}/>
                    <div>{player.playerPrestige}</div>
                  </div>
                  
                </div>
              </div>
            </Card>
          ))}
      </Card>
      <Modal
        aria-labelledby="contained-modal-title-vcenter"
        centered
        show={modalData.showing} onHide={() => setModalData({showing: false})}
      >
        <Modal.Header closeButton={true}>
          <Modal.Title>Player information</Modal.Title>
        </Modal.Header>
        <Modal.Body id="leader-modal-body">
          {getModalInner()}
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={() => setModalData({showing: false})}>Close</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default Leaderboard;