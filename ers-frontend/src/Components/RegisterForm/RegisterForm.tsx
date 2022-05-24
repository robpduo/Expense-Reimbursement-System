import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { Role } from '../../Interfaces/IUser';
import { logoutUser, newUser } from '../../Slices/UserSlice';
import { AppDispatch, RootState } from '../../Store';

import "./RegisterForm.css";
const RegisterForm: React.FC = () => {

    const userState = useSelector((state: RootState) => state.user);

    const [email, setEmail] = useState<string | any>("");
    const [firstName, setFirstName] = useState<string | any>("");
    const [lastName, setLastName] = useState<string | any>("");
    const [username, setUsername] = useState<string | any>("");
    const [password, setPassword] = useState<string | any>("");
    const [prompt, setPrompt] = useState<boolean>(false);

    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {

        if (event.target.className == "first-name-field") {
            setFirstName(event.target.value);
        } else if (event.target.className === "last-name-field") {
            setLastName(event.target.value);
        } else if (event.target.className === "email-field") {
            setEmail(event.target.value);
        } else if (event.target.className === "username-field") {
            setUsername(event.target.value);
        } else if (event.target.className === "password-field") {
            setPassword(event.target.value);
        }

        if (!email || !firstName || !lastName || !username || !password) {
            setPrompt(false);
        } else {
            setPrompt(true);
        }

    }

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        let newAccountDetails = {
            username: username,
            fName: firstName,
            lName: lastName,
            email: email,
            password: password,
            role: Role[Role.EMPLOYEE]
        }

        if (prompt === true) {
            dispatch(newUser(newAccountDetails));
        }
    }

    const handleCancel = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        navigator('/');
    }
    console.log(userState.key);
    return (
        
        <div className="register">

            <h1>Register New Employee</h1>

            <form className="registration-form" onSubmit={handleSubmit}>

                <h3 className="form-title">Enter Your Details Below</h3>
                <h3>First Name: <span><input type="text" className="first-name-field" onChange={handleChange}></input></span></h3>
                <h3>Last Name: <span><input type="text" className="last-name-field" onChange={handleChange}></input></span></h3>
                <h3>Email: <span><input type="email" className="email-field" onChange={handleChange}></input></span></h3>
                <h3>Username: <span><input type="text" className="username-field" onChange={handleChange}></input></span></h3>
                <h3>Password: <span><input type="password" className="password-field" onChange={handleChange}></input></span></h3>

                {prompt === false ? <h5 className="form-status">Form Incomplete</h5> : <h5 className="form-status-complete">Ready to Submit!</h5>}
                {userState.key === 1 ? <h5 className="form-status-complete">New Employee Registered!</h5> : userState.key === 2 ? <h5 className="form-status">Username Already Exists</h5> : <></>}

                <input type="submit" className="submit-button"></input>
                <button className="cancel-button" onClick={handleCancel} >Cancel</button>
            </form>
        </div>
    )
}

export default RegisterForm