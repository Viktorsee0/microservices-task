package com.specificgroup.ratingservice.service;

import com.specificgroup.ratingservice.dto.TaskEvent;
import com.specificgroup.ratingservice.domain.ScoreRow;
import com.specificgroup.ratingservice.domain.UserScore;

import java.util.List;
import java.util.UUID;

/**
 * This service includes the basic logic for evaluating the system rating
 */
public interface UserScoreService {

    /**
     * @return the current leader board ranked from high to low score
     */
    List<ScoreRow> getCurrentLeaderBoard(final int topCount);

    /**
     * @return the current user score
     */
    ScoreRow getCurrentUserScore(final UUID userId);

    /**
     * Update or create a {@link UserScore}
     */
    void updateScoreForUser(final TaskEvent event);
}

