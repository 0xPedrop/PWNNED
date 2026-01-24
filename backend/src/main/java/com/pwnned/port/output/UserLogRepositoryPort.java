package com.pwnned.port.output;

import com.pwnned.domain.model.UserLog;
import java.util.List;

public interface UserLogRepositoryPort {
    void save(UserLog log);
    List<UserLog> findAllByUserId(String userId);
}