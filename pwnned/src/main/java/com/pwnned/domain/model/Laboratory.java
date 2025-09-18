package com.pwnned.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.enums.LaboratoryType;

import java.util.Objects;
import java.util.UUID;

public class Laboratory {
    private UUID labId;
    private String title;
    private Difficulty difficulty;
    private LaboratoryType laboratoryType;

    @JsonBackReference
    private LearningPath learningPath;

    public Laboratory(UUID labId, String title, Difficulty difficulty, LaboratoryType laboratoryType, LearningPath learningPath) {
        this.labId = labId;
        this.title = title;
        this.difficulty = difficulty;
        this.laboratoryType = laboratoryType;
        this.learningPath = learningPath;
    }

    public Laboratory() {
    }

    public UUID getLabId() {
        return labId;
    }

    public void setLabId(UUID labId) {
        this.labId = labId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public LaboratoryType getLaboratoryType() {
        return laboratoryType;
    }

    public void setLaboratoryType(LaboratoryType laboratoryType) {
        this.laboratoryType = laboratoryType;
    }

    public LearningPath getLearningPath() {
        return learningPath;
    }

    public void setLearningPath(LearningPath learningPath) {
        this.learningPath = learningPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laboratory that = (Laboratory) o;
        return Objects.equals(labId, that.labId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labId);
    }
}