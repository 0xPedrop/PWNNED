package com.pwnned.adapter.output.jpa.repository.entity;

import com.pwnned.domain.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "learning_paths")
@EqualsAndHashCode(exclude = {"laboratories", "usersAcessing", "certificate"})
public class LearningPathEntity {
    @Id
    private Long learningPathId;

    private String title;
    private String category;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany(mappedBy = "learningPathsAcessed")
    private Set<UserEntity> usersAcessing;

    @OneToMany(mappedBy = "learningPath")
    private Set<LaboratoryEntity> laboratories;

    @OneToOne(mappedBy = "learningPath")
    private CertificateEntity certificate;
}