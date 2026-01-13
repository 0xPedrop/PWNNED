package com.pwnned.port.input;

import com.pwnned.domain.model.UserLog;
import java.util.List;

public interface UserLogServicePort {
    void logAction(String userId, String action);
    List<UserLog> getUserLogs(String userId);
}