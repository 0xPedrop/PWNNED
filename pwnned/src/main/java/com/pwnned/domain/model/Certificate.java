package com.pwnned.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Certificate {
    private Long certificateId;
    private String title;
    private LocalDate issueDate;
    private String serialNumber;
    private String url;
    private User user;
    private LearningPath learningPath;

    public Certificate(String title) {
        this.title = title;
        this.issueDate = LocalDate.now();
        this.serialNumber = generateUniqueSerialNumber();
    }

    public Certificate() {
    }

    public Certificate(Long certificateId, String title, LocalDate issueDate, String serialNumber, String url,
                       User user, LearningPath learningPath) {
        this.certificateId = certificateId;
        this.title = title;
        this.issueDate = issueDate;
        this.serialNumber = serialNumber;
        this.url = url;
        this.user = user;
        this.learningPath = learningPath;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LearningPath getLearningPath() {
        return learningPath;
    }

    public void setLearningPath(LearningPath learningPath) {
        this.learningPath = learningPath;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "certificateId=" + certificateId +
                ", title='" + title + '\'' +
                ", issueDate=" + issueDate +
                ", serialNumber='" + serialNumber + '\'' +
                ", user=" + (user != null ? user.getUserId() : null) +
                ", learningPath=" + (learningPath != null ? learningPath.getLearningPathId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(certificateId, that.certificateId)
                && Objects.equals(serialNumber, that.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificateId, serialNumber);
    }

    private String generateUniqueSerialNumber() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}