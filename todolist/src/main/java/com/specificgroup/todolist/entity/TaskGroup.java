package com.specificgroup.todolist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity(name = "task_group")
public final class TaskGroup {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private final UUID userId;


    @NotBlank
    @Column(name = "group_title")
    private String title;

    @OneToMany(mappedBy = "taskGroup", cascade = CascadeType.REMOVE)
    private List<Task> tasks;


    @Column(name = "created_at")
    @CreationTimestamp
    private final Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
}
