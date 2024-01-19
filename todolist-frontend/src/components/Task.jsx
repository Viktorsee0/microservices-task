import React, {useState} from 'react';
import TodolistApi from "../api/TodolistApi";

const Task = (props) => {
    const [task, setTask] = useState(props.task)


    const handleUpdate = () => {
        window.location.href = '/update-task?id=' + task.id;
    }

    const handleDone = () => {
        const doneTask = async () => {
            let response = await TodolistApi.doneTask(task.id);
            if (response.ok) {
                task.status = 'DONE';
                setTask(task);
            }
        }
        doneTask();
        // console.log(promise);
    }

    const componentStyle = {
        padding: '10px',
        margin: '20px',
    };
    const buttonStyle = {
        height: '25px',
        width: '90px',
    };
    return (
        <div style={componentStyle}>
            <h5>{props.task.title}</h5>
            <p>Task id: {props.task.id}</p>
            <p>Description: {props.task.description}</p>
            <p>Expire At: {props.task.expireAt}</p>
            <p>Status: {props.task.status}</p>
            <p>Created At: {props.task.createdAt}</p>
            <p>Updated At: {props.task.updatedAt}</p>
            <button style={buttonStyle} onClick={handleUpdate}>Update task</button>
            <button style={buttonStyle} disabled={props.task.status==='DONE' || props.task.status==='FAILED'} onClick={handleDone}>Done task</button>
            <button style={buttonStyle} onClick={() => props.handleDelete(props.task.id)}>Delete task</button>
        </div>
    );
};

export default Task;