package com.pwnned.domain.model;

import com.pwnned.domain.enums.Difficulty;

public class LearningPath {
    private Long pathId;
    private String title;
    private String description;
    private String category;
    private Difficulty difficulty;

    public LearningPath(Long pathId, String title, String description, String category, Difficulty difficulty) {
        this.pathId = pathId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}

