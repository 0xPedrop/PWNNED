package com.pwnned.adapter.output.jpa.repository.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "user_logs")
public class UserLogEntity {
    @Id
    private String id;
    private String userId;
    private String action;
    private LocalDateTime timestamp;
}