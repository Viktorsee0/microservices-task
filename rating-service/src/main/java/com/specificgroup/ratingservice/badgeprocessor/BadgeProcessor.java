package com.specificgroup.ratingservice.badgeprocessor;


import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.UserScore;

import java.util.Optional;

public interface BadgeProcessor {


    /**
     * Processes some or all of the passed parameters and decides if the user
     * is entitled to a badge.
     *
     * @return a BadgeType if the user is entitled to this badge, otherwise empty
     */
    Optional<BadgeType> processForOptionalBadge(UserScore scoreTask);

    /**
     * @return the BadgeType object that this processor is handling. You can use
     * it to filter processors according to your needs.
     */
    BadgeType badgeType();
}
