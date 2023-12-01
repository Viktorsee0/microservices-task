package com.specificgroup.ratingservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration with the different types of Badges that a user can win.
 * */
@RequiredArgsConstructor
@Getter
public enum BadgeType {
    // Badges depending on score
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),

    // Other badges won for different conditions
    FIRST_DONE("First time");
    private final String description;
}
