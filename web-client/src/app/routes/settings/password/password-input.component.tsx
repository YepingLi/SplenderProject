import React from "react";
import './password-input.component.scss';


interface PasswordProps {
    pwd: string;
    setPwd: React.Dispatch<React.SetStateAction<string>>
    labelValue: string
}

/**
 * Leave me an explanation!
 * @param param0 
 * @returns 
 */
const PasswordInput: React.FC<PasswordProps> =  ({pwd, setPwd, labelValue}) => {
    return(
        <div>
            <label className="password-label">{labelValue}:</label>
            <input 
                type="password"
                required 
                value={pwd}
                onChange={(e) => setPwd(e.target.value)}
            />
        </div>
    )
}
export default PasswordInput