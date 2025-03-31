package com.pwnned.domain.model;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.enums.LabType;

import java.util.UUID;

public class Laboratory {
    private UUID labId;
    private String title;
    private Difficulty difficulty;
    private LabType labType;

    public Laboratory(UUID labId, String title, Difficulty difficulty, LabType labType) {
        this.labId = labId;
        this.title = title;
        this.difficulty = difficulty;
        this.labType = labType;
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

    public LabType getLabType() {
        return labType;
    }

    public void setLabType(LabType labType) {
        this.labType = labType;
    }
}

