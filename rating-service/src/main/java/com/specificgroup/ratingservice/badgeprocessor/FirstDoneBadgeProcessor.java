package com.specificgroup.ratingservice.badgeprocessor;

import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.UserScore;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FirstDoneBadgeProcessor implements BadgeProcessor {

    @Override
    public Optional<BadgeType> processForOptionalBadge(final UserScore scoreTask) {
        return scoreTask.getTasks().size() == 1 ?
                Optional.of(BadgeType.FIRST_DONE) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.FIRST_DONE;
    }
}