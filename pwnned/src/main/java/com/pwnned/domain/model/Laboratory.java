package com.pwnned.domain.model;

import com.pwnned.domain.enums.Difficulty;

public class Laboratory {
    private Long labId;
    private String title;
    private Difficulty difficulty;
    private String instructions;
    private String videoLesson;

    public Laboratory(Long labId, String title, Difficulty difficulty, String instructions, String videoLesson) {
        this.labId = labId;
        this.title = title;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.videoLesson = videoLesson;
    }

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getVideoLesson() {
        return videoLesson;
    }

    public void setVideoLesson(String videoLesson) {
        this.videoLesson = videoLesson;
    }
}

