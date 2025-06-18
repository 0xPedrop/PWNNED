package com.pwnned.domain.model;

import com.pwnned.domain.enums.Difficulty;

import java.util.List;
import java.util.UUID;

public class LearningPath {
    private UUID learningPathId;
    private String title;
    private String category;
    private Difficulty difficulty;

    public LearningPath(UUID learningPathId, String title, String category, Difficulty difficulty) {
        this.learningPathId = learningPathId;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
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
}

