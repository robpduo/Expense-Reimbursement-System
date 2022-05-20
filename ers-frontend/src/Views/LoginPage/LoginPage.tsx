import React, { useEffect } from 'react';

import {useSelector} from 'react-redux';
import {RootState} from '../../Store';

import { useNavigate } from 'react-router-dom';

import { Login } from '../../Components/LoginForm';

import "./LoginPage.css";

export const LoginPage: React.FC = () => {
    const userState = useSelector((state:RootState)=> state.user);
    
    const navigator = useNavigate();

    useEffect(()=>{
        if(!userState.error && userState.user){
            navigator('/home');
        }
    }, [userState]);

    return(
        <div className="login-page">
            {userState.error ? <h2 className="login-error">Username or password incorrect</h2> : <></>}
            <Login />
        </div>
    )

}