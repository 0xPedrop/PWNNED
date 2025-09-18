package com.pwnned.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pwnned.domain.enums.Difficulty;

import java.util.Set;
import java.util.UUID;
import java.util.Objects;

public class LearningPath {
    private UUID learningPathId;
    private String title;
    private String category;
    private Difficulty difficulty;

    @JsonManagedReference
    private Set<Laboratory> laboratories;

    private Set<User> usersAcessing;
    private Certificate certificate;

    public LearningPath(UUID learningPathId, String title, String category, Difficulty difficulty,
                        Set<Laboratory> laboratories, Set<User> usersAcessing, Certificate certificate) {
        this.learningPathId = learningPathId;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.laboratories = laboratories;
        this.usersAcessing = usersAcessing;
        this.certificate = certificate;
    }

    public LearningPath() {
    }

    public UUID getLearningPathId() {
        return learningPathId;
    }

    public void setLearningPathId(UUID learningPathId) {
        this.learningPathId = learningPathId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<Laboratory> getLaboratories() {
        return laboratories;
    }

    public void setLaboratories(Set<Laboratory> laboratories) {
        this.laboratories = laboratories;
    }

    public Set<User> getUsersAcessing() {
        return usersAcessing;
    }

    public void setUsersAcessing(Set<User> usersAcessing) {
        this.usersAcessing = usersAcessing;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningPath that = (LearningPath) o;
        return Objects.equals(learningPathId, that.learningPathId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(learningPathId);
    }
}