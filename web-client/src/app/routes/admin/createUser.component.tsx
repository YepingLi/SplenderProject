import { useState } from "react";
import { AdminService } from "../../services/admin.service";


export function CreateUser() {
    const lb = new AdminService();
    const handleAddUser = (name: string, password: string, color: string, role:string) => {
        lb.adduser(name, password, color, role).catch(err => err.resp.text().then((x:string) => console.log(x)));
        console.log("a")
    }
    const [newUserName, setNewUserName] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [newColor, setColor] = useState('');
    const [role,setRole] = useState('ROLE_PLAYER');
    return (
        <form >
            <div className="input-group mb-3">
                <span className="input-group-text" id="inputGroup-sizing-default">New User Name</span>
                <input type="text" className="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"
                    value={newUserName}
                    onChange={(event) => setNewUserName(event.target.value)} />

            </div>
            <div className="input-group mb-3">
                <span className="input-group-text" id="inputGroup-sizing-default">Password</span>
                <input type="text" className="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"
                    value={newPassword}
                    onChange={(event) => setNewPassword(event.target.value)} />

            </div>
            <div className="input-group mb-3">
                <span className="input-group-text" id="inputGroup-sizing-default">Color</span>
                <input type="text" className="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"
                    value={newColor}
                    onChange={(event) => setColor(event.target.value)} />
            </div>

            <div className="input-group">
                <select className="form-select" id="inputGroupSelect04" aria-label="Example select with button addon" value={role} onChange={(event) => setRole(event.target.value)}>
                    <option defaultValue={"ROLE_PLAYER"}>Choose...</option>
                    <option value="ROLE_PLAYER">Player</option>
                    <option value="ROLE_ADMIN">Admin</option>
                </select>
                <button className="btn btn-outline-success" type="button" onClick={e =>handleAddUser(newUserName,newPassword,newColor,role)}>Add User</button>
            </div> 
        </form>
    )
}