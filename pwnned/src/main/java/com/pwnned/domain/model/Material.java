package com.pwnned.domain.model;

import com.pwnned.domain.enums.MaterialType;

public class Material {
    private Long materialId;
    private String title;
    private String author;
    private String category;
    private MaterialType type;

    public Material(Long materialId, String title, String author, String category, MaterialType type) {
        this.materialId = materialId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.type = type;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }
}
