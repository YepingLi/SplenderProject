import React, { useState } from "react";
const style = {
    fontSize: 15,
    marginLeft: 100,
    fontWeight: 500
};
const handleSubmitOld=()=>{
    // todo
};

interface a1{
    oldPwd: string;
    setOldPwd:React.Dispatch<React.SetStateAction<string>>
    handleSubmitOld: (event: React.FormEvent) => void;
}

const PasswordinputOld: React.FC<a1> = 
({oldPwd, setOldPwd, handleSubmitOld}) =>
{
    return(
        <form onSubmit={handleSubmitOld}>
        <label style={Object.assign({ marginRight: 25 }, style)}>Old Password: </label>
        <input 
            type="text"
            required 
            value={oldPwd}
            onChange={(e) => setOldPwd(e.target.value)}
        />
        
        {/* <textarea 
            required
            value={body}
            onChange={(e) => setBody(e.target.value)}
        ></textarea> */}

        <button type="submit">Change Old Password</button>
    </form>
    )
}
export default PasswordinputOld