// import React, {useEffect, useState} from "react";
import TodolistApi from "../api/TodolistApi";
import {useEffect, useState} from "react";


/**
 * {
 * "id": 4
 *   "title": "Task Title 9",
 *   "description": "Task Description 9",
 *   "expireAt" : "2024-10-11T08:19:37.698+00:00"
 * }
 *
 */
const TaskForm = () => {
    const [task, setTask] = useState({});

    let verb = 'Create';
    let expireAtInput = <input id="task_expireAt" type="text" placeholder="expireAt" value={task.expireAt}/>;


    let id = new URLSearchParams(window.location.search).get('id')
    if (id) {
        verb = 'Update';
        expireAtInput = null;
    }
    useEffect(() => {
        const getTaskById = async () => {
            const response = await TodolistApi.getTaskById(id);
            const task = await response.json();
            setTask(task);
        }
        if (id) {
            getTaskById();
        }
    }, []);

    const SendData = () => {
        const title = document.getElementById("task_title").value;
        const description = document.getElementById("task_description").value;
        let expireAt;

        if (id) {
            /**
             * дедлайн таски не меняется потому что на бэке так
             */
            expireAt = task.expireAt;
            let promise = TodolistApi.updateTask(id, title, description, expireAt);
            console.log(promise)
        } else {
            expireAt = document.getElementById("task_expireAt").value;
            let groupId = new URLSearchParams(window.location.search).get('groupId')
            let promise = TodolistApi.createTask(groupId, title, description, expireAt);
            console.log(promise)
        }
        window.location.href = '/home';
    };

    return (
        <div>
            <h1>{verb} a task</h1>
            <input id="task_title" type="text" placeholder="Title"/>
            <input id="task_description" type="text" placeholder="description"/>
            {expireAtInput}
            <button type="submit" onClick={SendData}>Submit</button>
        </div>
    );
};

export default TaskForm;