import React, {useState} from 'react';
import Task from "./Task";
import TodolistApi from "../api/TodolistApi";

// const testTask = {
//     "id": 4,
//     "title": "Task Title 4",
//     "description": "Task Description 4",
//     "expireAt": "2024-11-30T08:19:37.698+00:00",
//     "status": "DONE",
//     "createdAt": "2024-01-04T10:45:29.256+00:00",
//     "updatedAt": "2024-01-04T12:05:35.549+00:00"
// };

const Group = (props) => {
    const [group, setGroup] = useState(props.group)
    // const [handleUpdate, setHandleUpdate] = useState(props.handleUpdate)

    const componentStyle = {
        border: '1px solid black',
        padding: '10px',
        margin: '10px',
        marginRight: '850px'
    };
    const buttonStyle = {
        height: '25px',
        width: '100px',
    };

    const handleUpdate = () => {
        window.location.href = '/update-group?id=' + group.id;
    }

    const addTask = () => {
        window.location.href = '/add-task?groupId=' + group.id;
    }

    const deleteTask = (id) => {
        let promise = TodolistApi.deleteTask(id);
        // setGroup(group.tasks.filter(t => t.id !== id))

    }

    return (
        <div style={componentStyle}>
            <h3>{group.title}</h3>
            <p>Group id: {group.id}</p>
            <p>user id: {group.userId}</p>
            <div>tasks: {group.tasks.map(task => (
                <Task key={task.id} task={task} handleDelete={deleteTask}/>))}</div>
            <p>Created At: {group.createdAt}</p>
            <p>Updated At: {group.updatedAt}</p>
            {/*<this.DeleteButton id={this.state.id} style={buttonStyle} text="Delete group"></this.DeleteButton>*/}
            <button style={buttonStyle} onClick={addTask}>Add task</button>
            <button style={buttonStyle} onClick={handleUpdate}>Update group</button>
            <button style={buttonStyle} onClick={()=> props.handleDelete(props.group.id)}>Delete group</button>
        </div>
    );
};

export default Group;