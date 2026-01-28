package com.pwnned.adapter.output.jpa.repository.entity;

import com.pwnned.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude = {"learningPathsAcessed", "certificates"})
public class UserEntity {
    @Id
    private Long userId;

    private String email;

    private String password;

    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @ManyToMany
    @JoinTable(
            name = "user_learning_path",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "learning_path_id")
    )
    private Set<LearningPathEntity> learningPathsAcessed;

    @OneToMany(mappedBy = "user")
    private Set<CertificateEntity> certificates;

    private String profileImageUrl;
}
