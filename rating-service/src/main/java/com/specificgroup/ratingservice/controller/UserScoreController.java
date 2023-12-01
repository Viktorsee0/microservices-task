package com.specificgroup.ratingservice.controller;

import com.specificgroup.ratingservice.domain.ScoreRow;
import com.specificgroup.ratingservice.service.UserScoreService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * This class implements a REST API for the LeaderBoard service.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/score")
@RequiredArgsConstructor
class UserScoreController {
    private final UserScoreService leaderBoardService;

    @GetMapping("/leaders")
    public List<ScoreRow> getLeaderBoard(@RequestParam("top")
                                               @Min(3) @Max(15) int top) {
        log.info("Retrieving leaderboard top: {}", top);
        return leaderBoardService.getCurrentLeaderBoard(top);
    }

    @GetMapping("/{userId}")
    public ScoreRow getUserScore(@PathVariable("userId") UUID userId) {
        log.info("Retrieving leaderboard for user with id: {}", userId);
        return leaderBoardService.getCurrentUserScore(userId);
    }
}
