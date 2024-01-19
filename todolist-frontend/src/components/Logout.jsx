import React from 'react';

const Logout = () => {

    const logout = () => {
        sessionStorage.clear();
        window.location.href = '/';
    }
    return (
        <div>
            <button onClick={() => logout()}>Logout</button>
        </div>
    );
};

export default Logout;