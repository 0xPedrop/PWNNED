package com.pwnned.adapter.output.jpa.repository.entity;

import com.pwnned.domain.enums.PathStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_learning_path")
public class UserLearningPathEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "path_id")
    private LearningPathEntity learningPath;

    private PathStatus pathStatus;
}
