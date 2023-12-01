package com.specificgroup.ratingservice.service.impl;

import com.specificgroup.ratingservice.domain.Task;
import com.specificgroup.ratingservice.dto.TaskEvent;
import com.specificgroup.ratingservice.badgeprocessor.BadgeProcessor;
import com.specificgroup.ratingservice.client.UserClient;
import com.specificgroup.ratingservice.domain.BadgeCard;
import com.specificgroup.ratingservice.domain.BadgeType;
import com.specificgroup.ratingservice.domain.ScoreRow;
import com.specificgroup.ratingservice.domain.UserScore;
import com.specificgroup.ratingservice.dto.UserDTO;
import com.specificgroup.ratingservice.repository.BadgeRepository;
import com.specificgroup.ratingservice.repository.UserScoreRepository;
import com.specificgroup.ratingservice.service.UserScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public
class UserScoreServiceImpl implements UserScoreService {
    private final UserScoreRepository userScoreRepository;
    private final BadgeRepository badgeRepository;
    private final UserClient client;
    private final List<BadgeProcessor> badgeProcessors;


    @Override
    public List<ScoreRow> getCurrentLeaderBoard(final int topCount) {
        Pageable topTen = PageRequest.of(0, topCount);
        List<ScoreRow> scoreOnly = userScoreRepository.findLeaderBoard(topTen);
        return scoreOnly.stream().map(row -> {
            List<String> badges =
                    badgeRepository.findByUserIdOrderByBadgeTimestampDesc(
                                    row.getUserId()).stream()
                            .map(b -> b.getBadgeType().getDescription())
                            .collect(Collectors.toList());
            return row.withBadges(badges);
        }).collect(Collectors.toList());
    }

    @Override
    public ScoreRow getCurrentUserScore(UUID userId) {

        Optional<ScoreRow> optUserScore = userScoreRepository.findScoreByUserId(userId);

        ScoreRow userScoreRow = optUserScore.orElseThrow(() ->
                new RuntimeException(String.format("User with id %s doesn't exist", userId)));

        List<String> userBadges = badgeRepository.findByUserIdOrderByBadgeTimestampDesc(userId)
                .stream().map(b -> b.getBadgeType().getDescription())
                .collect(Collectors.toList());

        return userScoreRow.withBadges(userBadges);
    }

    @Transactional
    @Override
    public void updateScoreForUser(final TaskEvent event) {
        UserDTO user = client.getUserById(event.getUserId());

        UserScore userScore = userScoreRepository.findByUserId(event.getUserId())
                .orElseGet(() -> new UserScore(event.getUserId(), user.getUsername()));

        userScore.updateScore(event.getStatus());
        userScore.addTask(new Task(event.getTaskId(), event.getStatus(), event.getUserId()));
        processForBadges(userScore);
        userScoreRepository.save(userScore);
    }

    private void processForBadges(final UserScore scoreTask) {
        Set<BadgeType> alreadyGotBadges = badgeRepository
                .findByUserIdOrderByBadgeTimestampDesc(scoreTask.getUserId())
                .stream()
                .map(BadgeCard::getBadgeType)
                .collect(Collectors.toSet());

        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
                .map(bp -> bp.processForOptionalBadge(scoreTask))
                .flatMap(Optional::stream)
                .map(badgeType -> new BadgeCard(scoreTask.getUserId(), badgeType))
                .toList();
        badgeRepository.saveAll(newBadgeCards);
    }
}