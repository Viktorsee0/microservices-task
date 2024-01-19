import URL from "./URL";


class RegistrationApi {

    /**
     * {
     *     "username":"username",
     *     "email":"email@mail.ru",
     *     "password":"password",
     *     "confirmPassword":"password",
     *     "firstname":"firstname",
     *     "lastname":"lastname"
     * }
     * @type {string}
     */

    static USER_URL = '/user';
    static AUTH_END = '/auth';

    static async signup(firstname, lastname, email, username, password, confirmPassword) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + RegistrationApi.USER_URL + RegistrationApi.AUTH_END,
            {
                method: "POST",
                body: JSON.stringify({
                    username: username,
                    email: email,
                    password: password,
                    confirmPassword: confirmPassword,
                    firstname: firstname,
                    lastname: lastname
                }),
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                }
            }
        );
        return response;
    }

    static async login(username, password) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + RegistrationApi.USER_URL + RegistrationApi.AUTH_END + `?username=${username}&password=${password}`
        );
        return response;
    }

    static async getUser(username) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + RegistrationApi.USER_URL + `/${username}`,
            {
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            }
        );
        return response;
    }

}

export default RegistrationApi
