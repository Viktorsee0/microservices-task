import React from 'react';
import RegistrationApi from "../api/RegistrationApi";

const Login = () => {

    const SendData = async () => {
        const userName = document.getElementById("username_login").value;
        const password = document.getElementById("password_login").value;

        let response = await RegistrationApi.login(userName, password);
        console.log(response);

        if (response.ok) {
            let json = await response.json();
            console.log(json);
            const token = json["access_token"];
            console.log(token);
            sessionStorage.setItem('token', token);
            response = await RegistrationApi.getUser(userName);
            console.log(response);

            json = await response.json();
            console.log(json);
            const id = json.id;
            console.log(id);
            sessionStorage.setItem('id', id);

            const username = json.username;
            console.log(username);
            sessionStorage.setItem('username', username);

            window.location.href = '/home';
        } else {
            console.log("errorrrrrr on login")
        }


        // response.then(response => {
        //     response.json()
        //     if (response.ok) {
        //         const token = response.json().then(json => json["access_token"])
        //         console.log(token)
        //         sessionStorage.setItem('token', token);
        //         console.log(sessionStorage.getItem('token'));
        //
        //         let json = RegistrationApi.getUser(username).then(response => response.json());
        //         console.log(json);
        //
        //         const id = json.id;
        //         console.log(id);
        //         sessionStorage.setItem('id', id);
        //
        //         // window.location.href = '/home';
        //     } else {
        //         console.log("errorrrrrr on login")
        //     }
        // });

    }
    return (
        <div>
            <h1>Login</h1>
            <p>Username: </p>
            <input id="username_login" type="text" placeholder="username"/>
            <p>Password: </p>
            <input id="password_login" type="password" placeholder="password"/>

            <button type="submit" onClick={SendData}>Login!</button>
        </div>
    );
}
export default Login;