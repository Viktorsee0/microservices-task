package com.specificgroup.ratingservice.badgeprocessor;

import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.UserScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static com.specificgroup.ratingservice.badgeprocessor.GoldBadgeProcessor.MIN_GOLD_BADGE_SCORE;
import static org.assertj.core.api.Assertions.assertThat;

class GoldBadgeProcessorTest {
    private GoldBadgeProcessor badgeProcessor;
    private UserScore score;

    @BeforeEach
    public void setUp() {
        badgeProcessor = new GoldBadgeProcessor();
    }

    @Test
    @DisplayName("Should give gold badge")
    public void shouldGiveBadgeIfScoreOverThreshold() {
        score = new UserScore(UUID.randomUUID(), "username");
        score.setScore(MIN_GOLD_BADGE_SCORE);
        Optional<BadgeType> badgeType = badgeProcessor
                .processForOptionalBadge(score);
        assertThat(badgeType).contains(BadgeType.GOLD);
    }

    @Test
    @DisplayName("Should not give badge")
    public void shouldNotGiveBadgeIfScoreUnderThreshold() {
        score = new UserScore(UUID.randomUUID(), "username");
        score.setScore(MIN_GOLD_BADGE_SCORE - 1);
        Optional<BadgeType> badgeType = badgeProcessor
                .processForOptionalBadge(score);
        assertThat(badgeType).isEmpty();
    }

    @Test
    @DisplayName("Should return gold type")
    public void shouldReturnBronzeType() {
        BadgeType badgeType = badgeProcessor.badgeType();
        assertThat(badgeType).isEqualTo(BadgeType.GOLD);
    }
}