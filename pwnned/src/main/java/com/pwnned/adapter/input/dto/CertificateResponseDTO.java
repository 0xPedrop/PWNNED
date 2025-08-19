package com.pwnned.adapter.input.dto;

import java.time.LocalDate;

public record CertificateResponseDTO(String title, LocalDate issueDate, String serialNumber, String url) {
}
