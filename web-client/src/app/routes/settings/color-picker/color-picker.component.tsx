import { ColorResult, GithubPicker } from 'react-color';
import { useState } from 'react';


const ColorPicker = () => {
    

    const [color, setColor]= useState<any>(`rgba(${255},${0},${0},${1})`);
    
    

    const [displayPicker, setDisplayPicker] = useState(false);

    const handleClick = () => {
        setDisplayPicker(!displayPicker);
    };

    function handleChange(color: ColorResult){
        setColor(`rgba(${color.rgb.r}, ${color.rgb.g}, ${color.rgb.b}, ${color.rgb.a})`);
        //TODO: update color of user
    };

    //TODO: close the component on click!
    return (
        <div className='colorPicker'>
            <div style={{fontWeight: 500}}>Display Color: 
                <div className='colorButton' onClick={handleClick}>
                    <div className='innerColorButton' style={{background: color}}/>
                </div>
            </div>
            {displayPicker && <GithubPicker className='githubPicker' onChange={handleChange}/>}
        </div>
    );
}
 
export default ColorPicker;