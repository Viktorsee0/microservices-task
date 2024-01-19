import React from 'react';
import RegistrationApi from "../api/RegistrationApi";

const SignUp = () => {
    const SendData = () => {
        const firstname = document.getElementById("firstname_signup").value;
        const lastname = document.getElementById("lastname_signup").value;
        const email = document.getElementById("email_signup").value;
        const username = document.getElementById("username_signup").value;
        const password = document.getElementById("password_signup").value;
        const confirmPassword = document.getElementById("confirm_password_signup").value;

        let response = RegistrationApi.signup(firstname, lastname, email, username, password, confirmPassword);
        console.log(response);

        response.then(response => {
            if (response.ok) {
                // переходим дальше на login
                window.location.href = '/login';
            } else {
                let json = response.json();
                console.log(json);
            }
        });
    };

    // const buttonStyle = {
    //     height: '25px',
    //     width: '90px',
    // };
    return (
        <div>
            <h1>Registration</h1>
            <p>Firstname: </p>
            <input id="firstname_signup" type="text" placeholder="firstname"/>
            <p>Lastname: </p>
            <input id="lastname_signup" type="text" placeholder="lastname"/>
            <p>Email: </p>
            <input id="email_signup" type="text" placeholder="email"/>
            <p>Username: </p>
            <input id="username_signup" type="text" placeholder="username"/>
            <p>Password: </p>
            <input id="password_signup" type="password" placeholder="password"/>
            <p>Confirm Password: </p>
            <input id="confirm_password_signup" type="password" placeholder="confirm password"/>
            <button type="submit" onClick={SendData}>Sign Up!</button>
        </div>
    );
};

export default SignUp;