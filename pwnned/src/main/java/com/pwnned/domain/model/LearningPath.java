package com.pwnned.domain.model;

import com.pwnned.domain.enums.Difficulty;

import java.util.List;
import java.util.UUID;

public class LearningPath {
    private UUID pathId;
    private String title;
    private String category;
    private Difficulty difficulty;
    private List<Laboratory> laboratories;

    public LearningPath(UUID pathId, String title, String category, Difficulty difficulty,
                        List<Laboratory> laboratories) {
        this.pathId = pathId;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.laboratories = laboratories;
    }

    public LearningPath() {
    }

    public UUID getPathId() {
        return pathId;
    }

    public void setPathId(UUID pathId) {
        this.pathId = pathId;
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

    public List<Laboratory> getLaboratories() {
        return laboratories;
    }

    public void setLaboratories(List<Laboratory> laboratories) {
        this.laboratories = laboratories;
    }
}

