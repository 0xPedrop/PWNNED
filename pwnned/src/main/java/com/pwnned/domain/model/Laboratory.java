package com.pwnned.domain.model;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.enums.LaboratoryType;

import java.util.UUID;

public class Laboratory {
    private UUID labId;
    private String title;
    private Difficulty difficulty;
    private LaboratoryType laboratoryType;
    private UUID learningPathId;

    public Laboratory(UUID labId, String title, Difficulty difficulty, LaboratoryType laboratoryType, UUID learningPathId) {
        this.labId = labId;
        this.title = title;
        this.difficulty = difficulty;
        this.laboratoryType = laboratoryType;
        this.learningPathId = learningPathId;
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

    public UUID getLearningPathId() {
        return learningPathId;
    }

    public void setLearningPathId(UUID learningPathId) {
        this.learningPathId = learningPathId;
    }
}

