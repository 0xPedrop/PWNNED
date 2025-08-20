package com.pwnned.domain.model;

import com.pwnned.domain.enums.UserType;

import java.util.Set;
import java.util.UUID;
import java.util.Objects;

public class User {
    private UUID userId;
    private String email;
    private String password;
    private String username;
    private UserType userType;
    private Set<Certificate> certificates;
    private Set<LearningPath> learningPathsAcessed;

    public User(UUID userId, String email, String password, String username, UserType userType,
                Set<Certificate> certificates, Set<LearningPath> learningPathsAcessed) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.userType = userType;
        this.certificates = certificates;
        this.learningPathsAcessed = learningPathsAcessed;
    }

    public User() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Set<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<Certificate> certificates) {
        this.certificates = certificates;
    }

    public Set<LearningPath> getLearningPathsAcessed() {
        return learningPathsAcessed;
    }

    public void setLearningPathsAcessed(Set<LearningPath> learningPathsAcessed) {
        this.learningPathsAcessed = learningPathsAcessed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}