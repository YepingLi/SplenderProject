import React, { useEffect } from 'react';
import { useState } from "react";
import './settings.component.scss';
import PasswordInput from './password/password-input.component'
import { Button, Form } from 'react-bootstrap';
import SettingsService from '../../services/settings.service'
import { useDispatch } from 'react-redux';
import { addToast } from '../../actions/toast.actions';
import { LobbyService } from '../../services/lobby.service';
import { User } from '../../models/user';
import { LoaderComponent } from '../../../sharedComponents';
import { ResponseError } from '../../../shared/httpClient';

const settingeService = new SettingsService();
const lobby = new LobbyService();

export function SettingsComponent(props: { username: string }) {
    const [color, setColor] = useState<string | undefined>();
    const [user, setUser] = useState<User>();
    
    useEffect(() => {
        let aborter = new AbortController();
        lobby.getUser(props.username, aborter).then((u) => {
            setColor(u.preferredColour);
            setUser(u);
        });
        return () => {
            aborter.abort();
        }
    }, []);

    const [newPwd, setNewPwd] = useState<string>('');
    const [oldPwd, setOldPwd] = useState<string>('');
    const dispatch = useDispatch();

    const handleSubmitNewPassword = () => {
        if (newPwd !== '' && oldPwd !== '') {
            // Do you submission here! Sending both passwords to the backend
            return settingeService.changePsw(oldPwd, newPwd, props.username)
                .then(() => {
                    setNewPwd("");
                    setOldPwd("");
                    dispatch(addToast({ source: "Settings", msg: "Successfully changed password!" }))
                }).catch((err: ResponseError | DOMException) => {
                    if (err instanceof DOMException) {
                        return;
                    }
                    err.resp
                        .text()
                        .then(e => {
                            dispatch(addToast({source: "Settings", msg: `Failed to set a new password: ${e}`}));
                        });
                });
        }
        return Promise.resolve();
    };


    const handleSubmitNewColour = () => {
        return settingeService.changeColour(color, props.username)
            .then(() => {
                dispatch(addToast({ source: "Settings", msg: "Successfully changed coulor!" }))
            }).catch((err: ResponseError | DOMException) => {
                if (err instanceof DOMException) {
                    return;
                }
                err.resp
                    .text()
                    .then(e => {
                        dispatch(addToast({source: "Settings", msg: `Failed to set a new colour: ${e}`}));
                    });
            });
    };

    // If the component is null, wait until it is not.
    if (user === undefined) {
        return <LoaderComponent />
    }

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        handleSubmitNewPassword().then()
    }
    
    console.log(color)
    let colour = `#${color}`
    // Use react-bootstrap buttons and other stuff!!
    return (
        <Form id='settings' onSubmit={handleSubmit}>
            <span className='Title'>Settings</span>
            <div className='flex-div'>
                <div className='username'>Name: </div>
                <p>{props.username}</p>
            </div>
            <PasswordInput pwd={oldPwd} setPwd={setOldPwd} labelValue="Old Password" />
            <PasswordInput pwd={newPwd} setPwd={setNewPwd} labelValue="New Password" />
            <div className='flex-div'>
                <Form.Label htmlFor="colorInput">Color picker: </Form.Label>
                <Form.Control
                    type="color"
                    id="colorInput"
                    defaultValue={colour}
                    title="Choose your color"
                    onChange={(e) => setColor(e.target.value.replace("#", ""))}
                />
            </div>
            <div className='flex-div'>
                <div className='role'>Role: </div>
                <p>{user.role}</p>
            </div>

            <Button style = {{marginBottom:50}} type="submit">Change Password</Button>

            <Button onClick={handleSubmitNewColour}>Change Colour</Button>     
        </Form>
    )
}