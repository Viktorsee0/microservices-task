package com.specificgroup.ratingservice.badgeprocessor;

import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.UserScore;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SilverBadgeProcessor implements BadgeProcessor {
    public static long MIN_SILVER_BADGE_SCORE = 200;

    @Override
    public Optional<BadgeType> processForOptionalBadge(final UserScore scoreTask) {
        return scoreTask.getScore() >= MIN_SILVER_BADGE_SCORE ?
                Optional.of(BadgeType.SILVER) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.SILVER;
    }
}
