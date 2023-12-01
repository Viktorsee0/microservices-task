package com.specificgroup.ratingservice.domain;


import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.List;
import java.util.UUID;

/**
 * Represents a line in our score.
 */
@Value
@AllArgsConstructor
public class ScoreRow {
    private UUID userId;
    private String username;
    private Long totalScore;
    @With
    List<String> badges;

    public ScoreRow(final UUID userId,
                    final String username,
                    final Long totalScore) {
        this.userId = userId;
        this.username = username;
        this.totalScore = totalScore;
        this.badges = List.of();
    }
}