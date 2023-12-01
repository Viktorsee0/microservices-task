package com.specificgroup.ratingservice.service;

import com.specificgroup.ratingservice.badgeprocessor.BadgeProcessor;
import com.specificgroup.ratingservice.client.UserClient;
import com.specificgroup.ratingservice.domain.BadgeCard;
import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.ScoreRow;
import com.specificgroup.ratingservice.repository.BadgeRepository;
import com.specificgroup.ratingservice.repository.UserScoreRepository;
import com.specificgroup.ratingservice.service.impl.UserScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserScoreServiceTest {
    private UserScoreService userScoreService;

    @Mock
    private UserScoreRepository userScoreRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private UserClient client;
    @Mock
    private List<BadgeProcessor> badgeProcessors;

    @BeforeEach
    public void setUp() {
        userScoreService = new UserScoreServiceImpl(
                userScoreRepository, badgeRepository, client, badgeProcessors
        );
    }

    @Test
    @DisplayName("The leaderboard should show the top 1")
    public void getCurrentLeaderBoard() {
        // given
        UUID id = UUID.randomUUID();
        ScoreRow scoreRow = new ScoreRow(id, "username", 300L, List.of());
        given(userScoreRepository.findLeaderBoard(any(Pageable.class))).willReturn(List.of(scoreRow));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(any(UUID.class)))
                .willReturn(List.of(new BadgeCard(UUID.randomUUID(), BadgeType.FIRST_DONE)));

        // when
        List<ScoreRow> leaderBoard = userScoreService.getCurrentLeaderBoard(1);

        // then
        List<ScoreRow> expectedLeaderBoard = List.of(
                new ScoreRow(id, "username", 300L,
                        List.of(BadgeType.FIRST_DONE.getDescription()))
        );
        then(leaderBoard).isEqualTo(expectedLeaderBoard);
    }

    @Test
    @DisplayName("Should receive a user score")
    public void getCurrentUserScore() {
        // given
        UUID id = UUID.randomUUID();
        ScoreRow scoreRow = new ScoreRow(id, "username", 300L, List.of());
        given(userScoreRepository.findScoreByUserId(any(UUID.class))).willReturn(Optional.of(scoreRow));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(any(UUID.class)))
                .willReturn(List.of(
                        new BadgeCard(UUID.randomUUID(), BadgeType.FIRST_DONE),
                        new BadgeCard(UUID.randomUUID(), BadgeType.BRONZE)
                ));

        // when
        ScoreRow leaderBoard = userScoreService.getCurrentUserScore(id);

        // then
        ScoreRow expectedLeaderBoard = new ScoreRow(id, "username", 300L,
                List.of(BadgeType.FIRST_DONE.getDescription(), BadgeType.BRONZE.getDescription())
        );
        then(leaderBoard).isEqualTo(expectedLeaderBoard);
    }

    @Test
    @DisplayName("Should receive an exception")
    public void getCurrentUserScoreIfUserDoesntExist() {
        // given
        given(userScoreRepository.findScoreByUserId(any(UUID.class))).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() ->
                userScoreService.getCurrentUserScore(UUID.randomUUID()))
                .isInstanceOf(RuntimeException.class);
    }
}