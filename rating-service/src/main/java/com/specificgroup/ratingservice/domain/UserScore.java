package com.specificgroup.ratingservice.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class represents the Score
 */
@Entity(name = "user_score")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScore {

    // The default score assigned to this card.
    public static final int DEFAULT_DONE_SCORE = 20;
    public static final int DEFAULT_FAIL_SCORE = 10;

    @Id
    @Column(name = "user_id")
    private UUID userId;
    private String username;
    private Long score;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private List<Task> tasks = new ArrayList<>();

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    public UserScore(UUID userId, String username) {
        this(userId, username, 0L, new ArrayList<>(),
                Timestamp.valueOf(LocalDateTime.now()));
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setUserId(userId);
    }

    public void updateScore(Status status) {
        if (status.equals(Status.DONE)) {
            this.score += DEFAULT_DONE_SCORE;
        } else if (status.equals(Status.FAILED)) {
            this.score -= DEFAULT_FAIL_SCORE;
        }
    }
}