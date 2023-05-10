import { useEffect, useState } from "react";
import { User } from "../../models/user"
import { AdminService } from "../../services/admin.service";
import SettingsService from "../../services/settings.service";

const lb = new AdminService();
const ss =  new SettingsService();
 export function AllUserComponent() {
  const [newPasswords, setNewPasswords] = useState("");

  const [users, setUsers] = useState<User[]>([]);
    
    useEffect(() => {
      lb.getallUsers().then((users) => {
        setUsers(users);
      });
    }, [users]);
    const handleDeleteUser = (name:string) => {
        lb.deleteUser(name)
    }
    const handleChangePassword = (newPassword : string, user: User)=>{
        ss.changePsw(user.password, newPassword,user.name)
    }
    let index =0
    return (
        <div>
        <h2>User List</h2>
        <ul>
          {users.filter(user => (user.role === "ROLE_PLAYER" )).map((user) => (
             <li key={index++}>
             {user.name}
             <form>
             <input
                type="password"
                placeholder="Enter new password"
                onChange={(event) => setNewPasswords(event.target.value)}
              />
              <button type="button" className="btn btn-secondary" onClick={() => handleChangePassword(newPasswords,user)}>Submit</button>
             </form>
             
             <button type="button" className="btn btn-danger" onClick={() => handleDeleteUser(user.name)}>Delete this user</button>
           </li>
    
          ))}
          
        </ul>
        
      </div>
    );
    
}