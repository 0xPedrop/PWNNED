package com.pwnned.adapter.output.jpa.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "certificates")
@EqualsAndHashCode(exclude = {"user", "learningPath"})
public class CertificateEntity {

    @Id
    private Long certificateId;

    private String title;
    private LocalDate issueDate;

    @Column(nullable = false, unique = true)
    private String serialNumber;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id")
    private LearningPathEntity learningPath;
}