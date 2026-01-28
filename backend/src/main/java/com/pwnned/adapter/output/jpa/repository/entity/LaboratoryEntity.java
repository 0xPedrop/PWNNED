package com.pwnned.adapter.output.jpa.repository.entity;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.enums.LaboratoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "laboratories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LaboratoryEntity {

    @Id
    private Long labId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LaboratoryType laboratoryType;

    @ManyToOne
    @JoinColumn(name = "learning_path_id", nullable = false)
    private LearningPathEntity learningPath;
}