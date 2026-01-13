package com.pwnned.adapter.output.jpa.repository.mongo;

import com.pwnned.adapter.output.jpa.repository.mongo.UserLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserLogMongoRepository extends MongoRepository<UserLogEntity, String> {
    List<UserLogEntity> findByUserId(String userId);
}