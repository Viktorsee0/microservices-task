import React, {useEffect, useState} from 'react';
import TodolistApi from "../api/TodolistApi";
import RatingApi from "../api/RatingApi";
import ScoreRow from "./ScoreRow";
import Group from "./Group";
import LeaderBoard from "./LeaderBoard";
import Logout from "./Logout";

const GroupsComponent = () => {

    const defaultScore = {
        userId: sessionStorage.getItem('id'),
        username: sessionStorage.getItem('username'),
        totalScore: 0,
        badges: [],
    };

    const [score, setScore] = useState(defaultScore);
    const [scores, setScores] = useState([]);
    const [groups, setGroups] = useState([]);


    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (!token) {
            window.location.href = '/';
        }
        const id = sessionStorage.getItem('id');

        const getPersonalScore = async () => {
            const userScore = await RatingApi.getUserScore(id);
            if (userScore.ok) {
                const json = await userScore.json();
                setScore(json);
            }
        }

        const getLeaderBoard = async () => {
            const scores = await RatingApi.getLeaderBoard(5);
            if (scores.ok) {
                const json = await scores.json();
                setScores(json);
            }
        }

        const getUserGroups = async () => {
            const groups = await TodolistApi.getUserGroups(id);
            if (groups.ok) {
                const json = await groups.json();
                setGroups(json);
            }
        }

        getPersonalScore();
        getLeaderBoard();
        getUserGroups();

    }, []);

    useEffect(() => {

    }, []);


    const handleAdd = () => {
        window.location.href = '/add-group';
    }

    const deleteGroup = (id) => {
        let promise = TodolistApi.deleteGroup(id);
    }

    return (
        <div>
            <Logout/>
            <ScoreRow style={{
                border: '3px solid bold',
                padding: '10px',
                margin: '10px',
                marginRight: '850px',
            }} score={score}/>
            <LeaderBoard scores={scores}/>
            <button onClick={handleAdd}>Add group</button>
            <div>
                {groups.map(group => {
                    return <Group key={group.id} group={group} handleDelete={deleteGroup}/>
                })}
            </div>
        </div>
    );
};

export default GroupsComponent;