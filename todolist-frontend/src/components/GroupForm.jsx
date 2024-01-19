import React from "react";
import TodolistApi from "../api/TodolistApi";

/**
 * {
 * "id": 7,
 *   "userId": "decbc1a0-608e-4ef7-9b1f-0e953d369ebd",
 *   "title": "old group Title",
 *   "tasks": [
 *   {
 *         "id": 5,
 *         "title": "Task Title 5",
 *         "description": "Task Description 5",
 *         "expireAt": "2024-11-30T08:19:37.698+00:00",
 *         "status": "IN_PROGRESS",
 *         "createdAt": "2024-01-09T11:08:27.909+00:00",
 *         "updatedAt": "2024-01-09T11:08:27.909+00:00"
 *     }
 *     ]
 * }
 */

const GroupForm = () => {
    let verb = 'Create';
    let id = new URLSearchParams(window.location.search).get('id')
    if (id) {
        verb = 'Update';
    }
    const SendData = () => {
        const title = document.getElementById("group_title").value;
        const userId = sessionStorage.getItem('id');

        if (id) {
            let promise = TodolistApi.updateGroup(id, title, userId);
            console.log(promise)
        } else {
            let promise = TodolistApi.createGroup(title, userId);
            console.log(promise)
        }
        window.location.href = '/home';
    };

    return (
        <div>
            <h1>{verb} a group</h1>
            <input id="group_title" type="text" placeholder="Title"/>
            <button type="submit" onClick={SendData}>Submit</button>
        </div>
    );
}

export default GroupForm;