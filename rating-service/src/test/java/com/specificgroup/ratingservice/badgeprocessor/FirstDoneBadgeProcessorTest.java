package com.specificgroup.ratingservice.badgeprocessor;

import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.Status;
import com.specificgroup.ratingservice.domain.Task;
import com.specificgroup.ratingservice.domain.UserScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FirstDoneBadgeProcessorTest {
    private FirstDoneBadgeProcessor badgeProcessor;
    private UserScore score;

    @BeforeEach
    public void setUp() {
        badgeProcessor = new FirstDoneBadgeProcessor();
    }

    @Test
    @DisplayName("Should give first done badge")
    public void shouldGiveBadgeIfScoreOverThreshold() {
        score = new UserScore(UUID.randomUUID(), "username");
        score.addTask(new Task(1L, Status.DONE, UUID.randomUUID()));
        Optional<BadgeType> badgeType = badgeProcessor
                .processForOptionalBadge(score);
        assertThat(badgeType).contains(BadgeType.FIRST_DONE);
    }

    @Test
    @DisplayName("Should not give badge")
    public void shouldNotGiveBadgeIfScoreUnderThreshold() {
        score = new UserScore(UUID.randomUUID(), "username");
        Optional<BadgeType> badgeType = badgeProcessor
                .processForOptionalBadge(score);
        assertThat(badgeType).isEmpty();
    }

    @Test
    @DisplayName("Should return  first done type")
    public void shouldReturnBronzeType() {
        BadgeType badgeType = badgeProcessor.badgeType();
        assertThat(badgeType).isEqualTo(BadgeType.FIRST_DONE);
    }
}