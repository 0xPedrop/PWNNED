package com.pwnned.adapter.output.mongo;

import com.pwnned.domain.model.UserLog;
import com.pwnned.port.output.UserLogRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserLogRepositoryAdapter implements UserLogRepositoryPort {
    private final UserLogMongoRepository repository;

    public UserLogRepositoryAdapter(UserLogMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(UserLog log) {
        UserLogEntity entity = new UserLogEntity();
        entity.setUserId(log.userId());
        entity.setAction(log.action());
        entity.setTimestamp(log.timestamp());
        repository.save(entity);
    }

    @Override
    public List<UserLog> findAllByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(e -> new UserLog(e.getId(), e.getUserId(), e.getAction(), e.getTimestamp()))
                .toList();
    }
}