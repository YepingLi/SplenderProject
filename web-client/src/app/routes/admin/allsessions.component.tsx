import { useEffect, useState } from "react";
import { AdminService } from "../../services/admin.service";
import Session from "../../models/sessions";
import { LobbyService } from "../../services/lobby.service";
import Player from "../../models/player";

const lb = new LobbyService();
export function AllSessions() {
    const [sessions, setSessions] = useState<Session[]>([]);

    useEffect(() => {
        lb.getSessions().then((sessions) => {
            setSessions(sessions);
        });
    }, [sessions]);
    const handleDeleteSession = (id: string) => {
        lb.deleteSession(id).catch(err => err.resp.text().then((x: string) => console.log(x)));
    }

    let index = 0
    return (
        <div>
            <h2>Sessions List</h2>
            
            
            
            <ul>
                {sessions.map((session) => (
                    <>

                        <li key={index++}>
                            Game ID:_____      
                            {session.id}
                            Creator:______
                            {session.creator}
                            {session.players.map((player) => (
                                <p>{player}</p>
                            ))}
                            <button onClick={() => handleDeleteSession(session.id)}>Delete</button>
                        </li>
                    </>


                ))}

            </ul>

        </div>
    );
}