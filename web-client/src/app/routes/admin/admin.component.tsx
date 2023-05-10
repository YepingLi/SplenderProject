import React, { useEffect, useMemo, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { sessionSelector, updateSessions } from '../../actions/sessions.actions';
import Session from '../../models/sessions';
import { LobbyService } from '../../services/lobby.service';
import './admin.component.scss';
import { AllUserComponent } from './allUser.component';
import { AllSessions } from './allsessions.component';
import { User } from '../../models/user';
import { CreateUser } from './createUser.component';

export function AdminComponent() {
    const [activeTab, setActiveTab] = useState("allusers");
    const handleTab1 = () => {
        // update the state to tab1
        setActiveTab("allusers");
    };
    const handleTab3 = () => {
        // update the state to tab1
        setActiveTab("addUser");
    };
    const handleTab2 = () => {
        // update the state to tab2
        setActiveTab("sessions");
    };
    
    return (
        <div >
            <h1>Admin Page</h1>
            <ul className="nav nav-tabs">
                <li className={activeTab === "allusers" ? "active" : "" } onClick={handleTab1}>
                <a className="nav-link" >All Users</a>
                </li>
                <li className={activeTab === "sessions" ? "active" : ""} onClick={handleTab2}>
                <a className="nav-link" >Sessions</a>
                </li> 
                <li className={activeTab === "addUser" ? "active" : ""} onClick={handleTab3}>
                <a className="nav-link" >Create New User</a>
                </li>               
            </ul>
            <div className="outlet">
                    {(()=> {
                        switch (activeTab){
                        case "allusers":return<AllUserComponent></AllUserComponent>;
                        case "addUser" : return <CreateUser></CreateUser>;
                        case "sessions" : return <AllSessions></AllSessions>;
                        
                    }
                    })()}
            </div>
        </div>
    );
}