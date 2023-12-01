package com.specificgroup.ratingservice.repository;

import com.specificgroup.ratingservice.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Handles data operations with BadgeCards
 * */
public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {

    /**
     * Retrieves all BadgeCards for a given user.
     *
     * @param userId the id of the user to look for BadgeCards
     * @return the list of BadgeCards, ordered by most recent first.
     * */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(UUID userId);
}
