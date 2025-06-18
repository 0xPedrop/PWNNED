package com.pwnned.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Certificate {
    private UUID certificateId;
    private String title;
    private LocalDate issueDate;
    private String serialNumber;
    private String url;

    public Certificate(String title, String url) {
        this.title = title;
        this.issueDate = LocalDate.now();
        this.serialNumber = generateUniqueSerialNumber();
        this.url = url;
    }

    public Certificate() {
    }

    public Certificate(UUID certificateId, String title, LocalDate issueDate, String serialNumber,
                       String url) {
        this.certificateId = certificateId;
        this.title = title;
        this.issueDate = issueDate;
        this.serialNumber = serialNumber;
        this.url = url;
    }

    public UUID getCertificateId() {
        return certificateId;
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

    @Override
    public String toString() {
        return "Certificate{" +
                "certificateId=" + certificateId +
                ", title='" + title + '\'' +
                ", issueDate=" + issueDate +
                ", serialNumber='" + serialNumber + '\'' +
                ", url='" + url + '\'' +
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
