package com.pwnned.adapter.output.jpa.repository.entity;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.enums.LaboratoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "laboratories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LaboratoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID labId;

    private String title;
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LaboratoryType laboratoryType;

    @Column(name = "learning_path_id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID learningPathId;
}
