import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { updateUser } from '../../Slices/UserSlice';
import { AppDispatch, RootState } from '../../Store';
import NavBarSelector from '../NavBarSelector/NavBarSelector'

import "./EditUserPage.css";

const EditUserPage: React.FC = () => {
    const userState = useSelector((state: RootState) => state.user);
    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    const [changeEmail, setEmail] = useState<string | any>(userState.user?.email);
    const [changeFirstName, setFirstName] = useState<string | any>(userState.user?.fName);
    const [changeLastName, setLastName] = useState<string | any>(userState.user?.lName);
    const [changeUsername, setUsername] = useState<string | any>(userState.user?.username);
    const [changePassword, setPassword] = useState<string | any>("");

    const[prompt, setPrompt] = useState<boolean>(false);

    useEffect(() => {
        //check if any user is logged in
        if (!userState.user) {
            navigator('/');
        }
        //initialize component state to current user
    }, []);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPrompt(false);
        if (event.target.className === "edit-field-fname") {
            setFirstName(event.target.value);
        } else if (event.target.className === "edit-field-lname") {
            setLastName(event.target.value);
        } else if (event.target.className === "edit-field-email") {
            setEmail(event.target.value);
        } else if (event.target.className === "edit-field-username") {
            setUsername(event.target.value);
        } else if (event.target.className === "edit-field-password") {
            setPassword(event.target.value);
        }

    } 

    const handleSubmit = (event: React.MouseEvent<HTMLHeadingElement>) => {
        let newAccountDetails = {
            username: changeUsername, 
            email: changeEmail,
            fName: changeFirstName,
            lName: changeLastName,
            password: changePassword
        }
        setPrompt(true);
        dispatch(updateUser(newAccountDetails));
    }

    const handleCancel = (event: React.MouseEvent<HTMLHeadingElement>) => {
        navigator("/");
    }

    return (
        <div className="container" >
            <NavBarSelector />
            <div className="background">
                <div className="welcome-canvas">
                    <div className='text-box'>
                        <h1 className="title">Edit Account Information</h1>
                        <h3 className="first-name">First Name:<span><input className="edit-field-fname" type="text" placeholder={userState.user?.fName} onChange={handleChange}></input></span></h3>
                        <h3 className="last-name">Last Name: <span><input className="edit-field-lname" type="text" placeholder={userState.user?.lName} onChange={handleChange}></input></span></h3>
                        <h3 className="email">Email: <span><input className="edit-field-email" type="text" placeholder={userState.user?.email} onChange={handleChange}></input></span></h3>
                        <h3 className="password">password:<span><input className="edit-field-password" type="password" placeholder="Enter New Password" onChange={handleChange}></input></span></h3>
                        <h3 className="username">Username: {userState.user?.username}</h3>
                        <h3 className="role">Role: {userState.user?.role}</h3>
                        <h3 className="account">Account ID: {userState.user?.userId}</h3>
                        {prompt == false ? <></> : <h3 className="prompter">Changes Submitted!</h3>}
                        <div className="button-wrapper">
                            <h3 className="button-submit" onClick={handleSubmit}>Submit</h3>
                            <h3 className="button-cancel" onClick={handleCancel}>Cancel</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EditUserPage

