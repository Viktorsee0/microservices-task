package com.specificgroup.ratingservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * This class links a Badge to a User. Contains also a timestamp with the moment in which the user got it.
 */
@Entity(name = "badge_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long badgeId;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "badge_timestamp")
    @EqualsAndHashCode.Exclude
    private long badgeTimestamp;

    @Column(name = "badge_type")
    @Enumerated(EnumType.STRING)
    private BadgeType badgeType;

    public BadgeCard(final UUID userId, final BadgeType badgeType) {
        this(null, userId, System.currentTimeMillis(), badgeType);
    }
}
