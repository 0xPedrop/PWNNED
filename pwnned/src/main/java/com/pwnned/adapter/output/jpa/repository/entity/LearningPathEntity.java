package com.pwnned.adapter.output.jpa.repository.entity;

import com.pwnned.domain.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "learning_paths")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(exclude = {"laboratories", "certificates", "usersAcessing"})
public class LearningPathEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "learning_path_id", nullable = false)
    private UUID learningPathId;

    private String title;
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @ManyToMany(mappedBy = "learningPathsAcessed")
    private Set<UserEntity> usersAcessing;

    @OneToMany(mappedBy = "learningPath")
    private Set<LaboratoryEntity> laboratories;

    @OneToOne(mappedBy = "learningPath")
    private CertificateEntity certificate;
}