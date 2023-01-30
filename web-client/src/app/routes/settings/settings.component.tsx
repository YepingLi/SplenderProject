import React from 'react';
import { useState } from "react";
import ColorPicker from './color-picker/color-picker.component';
import './settings.component.scss';
import PasswordInput from './password/password-input.component'
import { Button } from 'react-bootstrap';
import SettingsService from '../../services/settings.service'

export function SettingsComponent(props: {username: string}) {

    const [newPwd, setNewPwd] = useState<string>('');
    const [oldPwd, setOldPwd] = useState<string>('');
    const settingeservice = new SettingsService();
    const handleSubmitNewPassword = (event: React.FormEvent)=> {
        event.preventDefault();
        if (newPwd !== '' && oldPwd !== '') {
            // Do you submission here! Sending both passwords to the backend
            settingeservice.changePsw(oldPwd, newPwd)
            console.log("Submitted a good password now do something!");
            setNewPwd("");
            setOldPwd("");
        }
    };

//
/*
    const settingeservice = new SettingsService();

    function habdleChangePswEvent(theOldPwdList: string[], theNewPwdList: string[]){
        if((theOldPwdList.length > 0) && (theNewPwdList.length > 0)){
        let theOldPwd = theOldPwdList[theOldPwdList.length - 1];
        let theNewPwd = theNewPwdList[theNewPwdList.length - 1];
        settingeservice.changePsw(theOldPwd, theNewPwd)
        }
    }

//
*/
    // Use react-bootstrap buttons and other stuff!!
    return (
        <div id='settings'>
            <span className='Title'>Settings</span>
            <p className='username'>Name: {props.username}</p>
            <form onSubmit={handleSubmitNewPassword}>
                <PasswordInput pwd={oldPwd} setPwd={setOldPwd} labelValue="Old Password"/>
                <PasswordInput pwd={newPwd} setPwd={setNewPwd} labelValue="New Password"/>
                <Button type="submit"> Change Password </Button>
            </form>
            <form>
                <ColorPicker/>
            </form>
            <p className='role'>Role: </p>
        </div>
    )
}