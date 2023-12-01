package com.specificgroup.ratingservice.repository;

import com.specificgroup.ratingservice.domain.ScoreRow;
import com.specificgroup.ratingservice.domain.UserScore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Handles data operations with ScoreTask
 */
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {


    /**
     * Retrieves a list of {@link ScoreRow}s representing the Leader Board * of users and their total score.
     *
     * @return the leader board, sorted by highest score first.
     */
    @Query("SELECT NEW com.specificgroup.ratingservice.domain.ScoreRow(s.userId, s.username, s.score) " +
            "FROM com.specificgroup.ratingservice.domain.UserScore as s ORDER BY s.score DESC")
    List<ScoreRow> findLeaderBoard(Pageable pageable);

    /**
     * Retrieves a list of {@link ScoreRow}s representing the Leader Board * of users and their total score.
     *
     * @return the leader board, sorted by highest score first.
     */
    @Query("SELECT NEW com.specificgroup.ratingservice.domain.ScoreRow(s.userId, s.username, s.score) " +
            "FROM com.specificgroup.ratingservice.domain.UserScore as s where s.userId = :userId")
    Optional<ScoreRow> findScoreByUserId(@Param("userId") UUID userId);

    /**
     * Retrieves an optional of {@link UserScore} representing the user score * of his total score.
     *
     * @return the user score.
     */
    Optional<UserScore> findByUserId(UUID userId);
}
