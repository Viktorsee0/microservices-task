import React from 'react';

const Identification = () => {
    let element;
    const token = sessionStorage.getItem('token');
    if (token) {
        element = (window.location.href = '/home');
    } else {
        element = (<div>
            <button onClick={() => {
                window.location.href = '/signup';
            }}>Sign Up!
            </button>

            <button onClick={() => {
                window.location.href = '/login';
            }}>Log in!
            </button>
        </div>);
    }
    return element;
};

export default Identification;