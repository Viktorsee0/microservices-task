package com.specificgroup.ratingservice.badgeprocessor;

import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.UserScore;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BronzeBadgeProcessor implements BadgeProcessor {

    public static long MIN_BRONZE_BADGE_SCORE = 100;

    @Override
    public Optional<BadgeType> processForOptionalBadge(final UserScore scoreTask) {
        return scoreTask.getScore() >= MIN_BRONZE_BADGE_SCORE ?
                Optional.of(BadgeType.BRONZE) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
