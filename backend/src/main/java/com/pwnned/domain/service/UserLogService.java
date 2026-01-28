package com.pwnned.domain.service;

import com.pwnned.domain.model.UserLog;
import com.pwnned.port.input.UserLogServicePort;
import com.pwnned.port.output.UserLogRepositoryPort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserLogService implements UserLogServicePort {
    private final UserLogRepositoryPort repositoryPort;

    public UserLogService(UserLogRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public void logAction(String userId, String action) {
        repositoryPort.save(new UserLog(userId, action));
    }

    @Override
    public List<UserLog> getUserLogs(String userId) {
        return repositoryPort.findAllByUserId(userId);
    }
}