import React from 'react';
import ScoreRow from "./ScoreRow";

const LeaderBoard = (props) => {
    // const [scores, setScores] = useState(props.scores)
    return (
        <div/* style={{
            border: '2px solid black',
            padding: '10px',
            margin: '10px',
            marginRight: '850px'
        }}*/>
            <h3>Top {props.scores.length}</h3>
            {props.scores.map(score => (
                <ScoreRow key={score.userId} score={score} style={{border: '1px solid black'}}/>))}
        </div>
    );
};

export default LeaderBoard;