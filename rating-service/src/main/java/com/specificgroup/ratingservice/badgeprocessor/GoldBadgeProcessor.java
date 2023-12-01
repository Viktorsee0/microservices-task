package com.specificgroup.ratingservice.badgeprocessor;

import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.UserScore;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GoldBadgeProcessor implements BadgeProcessor {
    public static long MIN_GOLD_BADGE_SCORE = 500;

    @Override
    public Optional<BadgeType> processForOptionalBadge(final UserScore scoreTask) {
        return scoreTask.getScore() >= MIN_GOLD_BADGE_SCORE ?
                Optional.of(BadgeType.GOLD) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.GOLD;
    }
}
