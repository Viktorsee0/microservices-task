import React from 'react';

/**
 * {
 *     "userId": "22d3809e-2197-4bb7-8494-6204dd72e520",
 *     "username": "username",
 *     "totalScore": 20,
 *     "badges": [
 *         "First time"
 *     ]
 * }
 */
const ScoreRow = (props) => {
    return (
        <div style={props.style}>
            <p>userId: {props.score.userId}</p>
            <p>username: {props.score.username}</p>
            <p>total score: {props.score.totalScore}</p>
            <p>badges: {props.score.badges}</p>
        </div>
    );
};

export default ScoreRow;