import React, {useState} from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser } from '../../Slices/UserSlice';
import { AppDispatch, RootState } from '../../UserStore';

import "./LoginForm.css"

export const Login: React.FC = () => {

    const [username, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const dispatch: AppDispatch = useDispatch();
    
    const handleInput = (event:React.ChangeEvent<HTMLInputElement>) => {
        if(event.target.name === "email"){
            setEmail(event.target.value);
        }
        else {
            setPassword(event.target.value);
        }
    }

    const handleLogin = (event:React.MouseEvent<HTMLButtonElement>) => {
        let credentials = {
            username,
            password,
        };

        dispatch(loginUser(credentials));
    }

    return(
        <div className="login">
            <div className="text-container">
                <h1 className="login-header">Expense Reimbursement System</h1>
            </div>
            <form className="login-form">
                <div className="input-div">
                    <h4 className="input-h4">Please Enter Email</h4>
                    <input autoComplete="off" className="login-input" type="text" placeholder="email" name="email" onChange={handleInput}/>
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Please Enter Password</h4>
                    <input className="login-input" type="password" name="password" placeholder="password" onChange={handleInput}/>
                </div>
            </form>
            <button className="login-button" onClick={handleLogin}>Login</button>
        </div>
    )

}