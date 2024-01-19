import URL from "./URL";


class TodolistApi {

    static GROUP_ENDPOINT = '/task-group';
    static TASK_ENDPOINT = '/task';
    static USER_GROUPS_ENDPOINT='/user-groups';


    static async getTaskById(id) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.TASK_ENDPOINT + `/${id}`,
            {
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            }
        );
        // const json = await response.json();
        // return json;
        return response;
    }

    static async getGroupById(id) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.GROUP_ENDPOINT + `/${id}`,
            {
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            }
        );
        // const json = await response.json();
        // return json;
        return response;
    }
    static async getUserGroups(id) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.GROUP_ENDPOINT + TodolistApi.USER_GROUPS_ENDPOINT +`/${id}`,
            {
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            }
        );
        // const json = await response.json();
        // return json;
        return response;
    }

    static async getAllGroups() {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.GROUP_ENDPOINT,
            {
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            });
        // const json = await response.json();
        // return json;
        return response;
    }

    static async createGroup(title, userId) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.GROUP_ENDPOINT ,
            {
                method: "POST",
                body: JSON.stringify({
                    userId: userId,
                    title: title,
                    tasks: [],
                }),
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,

                }
            });
        // const json = await response.json();
        // return json;
        return response;
    }

    static async updateGroup(id, title, userId) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.GROUP_ENDPOINT ,
            {
                method: "PUT",
                body: JSON.stringify({
                    id: id,
                    userId: userId,
                    title: title,
                    tasks: [],
                }),
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,

                }
            });
        // const json = await response.json();
        // return json;
        return response;
    }

    static async deleteGroup(id) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.GROUP_ENDPOINT + `?id=${id}`,
            {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            });
        return response;
    }

    static async createTask(groupId, title, description, expireAt) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.TASK_ENDPOINT + `?groupId=${groupId}`,
            {
                method: "POST",
                body: JSON.stringify({
                    title: title,
                    description: description,
                    expireAt: expireAt,
                }),
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,

                }
            });
        // const json = await response.json();
        // return json;
        return response;
    }

    static async updateTask(id, title, description, expireAt) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.TASK_ENDPOINT,
            {
                method: "PUT",
                body: JSON.stringify({
                    id: id,
                    title: title,
                    description: description,
                    expireAt: expireAt,
                }),
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            });
        // const json = await response.json();
        // return json;
        return response;
    }

    static async doneTask(id) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.TASK_ENDPOINT + `/done/${id}`,
            {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            });
        // const json = await response.json();
        // return json;
        return response;
    }

    static async deleteTask(id) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + TodolistApi.TASK_ENDPOINT+ `?id=${id}`,
            {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            });
        // const json = await response.json();
        return response;
    }

}

export default TodolistApi;