package com.specificgroup.ratingservice.badgeprocessor;

import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.UserScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static com.specificgroup.ratingservice.badgeprocessor.SilverBadgeProcessor.MIN_SILVER_BADGE_SCORE;
import static org.assertj.core.api.Assertions.assertThat;

class SilverBadgeProcessorTest {
    private SilverBadgeProcessor badgeProcessor;
    private UserScore score;

    @BeforeEach
    public void setUp() {
        badgeProcessor = new SilverBadgeProcessor();
    }

    @Test
    @DisplayName("Should give silver badge")
    public void shouldGiveBadgeIfScoreOverThreshold() {
        score = new UserScore(UUID.randomUUID(), "username");
        score.setScore(MIN_SILVER_BADGE_SCORE);
        Optional<BadgeType> badgeType = badgeProcessor
                .processForOptionalBadge(score);
        assertThat(badgeType).contains(BadgeType.SILVER);
    }

    @Test
    @DisplayName("Should not give badge")
    public void shouldNotGiveBadgeIfScoreUnderThreshold() {
        score = new UserScore(UUID.randomUUID(), "username");
        score.setScore(MIN_SILVER_BADGE_SCORE-1);
        Optional<BadgeType> badgeType = badgeProcessor
                .processForOptionalBadge(score);
        assertThat(badgeType).isEmpty();
    }

    @Test
    @DisplayName("Should return silver type")
    public void shouldReturnBronzeType() {
        BadgeType badgeType = badgeProcessor.badgeType();
        assertThat(badgeType).isEqualTo(BadgeType.SILVER);
    }
}