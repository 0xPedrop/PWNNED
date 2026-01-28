package com.pwnned.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pwnned.adapter.output.jpa.repository.util.SnowflakeIdGenerator;
import com.pwnned.adapter.output.redis.LearningPathRedisAdapter;
import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.exception.LearningPathNotFoundException;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.port.input.LearningPathServicePort;
import com.pwnned.port.output.CertificateRepositoryPort;
import com.pwnned.port.output.LaboratoryRepositoryPort;
import com.pwnned.port.output.LearningPathRepositoryPort;

import jakarta.transaction.Transactional;

@Service
public class LearningPathService implements LearningPathServicePort {

    private final LearningPathRepositoryPort learningPathRepositoryPort;
    private final LearningPathRedisAdapter learningPathRedisAdapter;
    private final LaboratoryRepositoryPort laboratoryRepositoryPort;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final CertificateRepositoryPort certificateRepositoryPort;

    public LearningPathService(LearningPathRepositoryPort learningPathRepositoryPort,
                               LearningPathRedisAdapter learningPathRedisAdapter,
                               LaboratoryRepositoryPort laboratoryRepositoryPort,
                               SnowflakeIdGenerator snowflakeIdGenerator, 
                               CertificateRepositoryPort certificateRepositoryPort) {
        this.learningPathRepositoryPort = learningPathRepositoryPort;
        this.learningPathRedisAdapter = learningPathRedisAdapter;
        this.laboratoryRepositoryPort = laboratoryRepositoryPort;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
        this.certificateRepositoryPort = certificateRepositoryPort;
    }

    @Override
    public LearningPath createLearningPath(LearningPath learningPath) {
        learningPath.setLearningPathId(snowflakeIdGenerator.nextId());
        return learningPathRepositoryPort.save(learningPath);
    }

    @Override
    public Page<LearningPath> getAllLearningPaths(Pageable pageable) {
        return learningPathRepositoryPort.findAll(pageable);
    }

    @Override
    public LearningPath getSingleLearningPath(Long learningPathId) {
        Optional<LearningPath> cachedLearningPath = learningPathRedisAdapter.getCachedLearningPath(learningPathId);

        if (cachedLearningPath.isPresent()) {
            return cachedLearningPath.get();
        }

        LearningPath learningPath = learningPathRepositoryPort.findById(learningPathId)
                .orElseThrow(() -> new LearningPathNotFoundException("Learning Path not found with ID: "
                        + learningPathId));

        learningPathRedisAdapter.cacheLearningPath(learningPath);
        return learningPath;
    }

    @Override
    public void deleteLearningPath(Long learningPathId) {
        Optional<LearningPath> learningPath = learningPathRepositoryPort.findById(learningPathId);
        if (learningPath.isEmpty()) throw new LearningPathNotFoundException("Learning Path "
                + learningPathId + " Not Found");
        learningPathRepositoryPort.deleteById(learningPathId);

        learningPathRedisAdapter.invalidateCacheForLearningPath(learningPathId);
    }

    @Override
    @Transactional 
    public void deleteAllLearningPaths(Pageable pageable) {
        Page<LearningPath> learningPaths = learningPathRepositoryPort.findAll(pageable);
        
        learningPaths.forEach(lp -> {
            laboratoryRepositoryPort.deleteAllByLearningPathId(lp.getLearningPathId());
            certificateRepositoryPort.deleteByLearningPathId(lp.getLearningPathId()); 
        });

        learningPathRepositoryPort.deleteAll();
        learningPathRedisAdapter.invalidateAllLearningPathsCache();
    }

    @Override
    public List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty) {
        Optional<List<LearningPath>> cachedLearningPathsByDifficulty = learningPathRedisAdapter
                                                            .getCachedLearningPathsByDifficulty(difficulty.name());

        if (cachedLearningPathsByDifficulty.isPresent()) {
            return cachedLearningPathsByDifficulty.get();
        }

        List<LearningPath> learningPaths = learningPathRepositoryPort.getLearningPathsByDifficulty(difficulty);
        if (learningPaths.isEmpty()) throw new LearningPathNotFoundException("Learning Paths Not Found");

        learningPathRedisAdapter.cacheLearningPathByDifficulty(difficulty.name(), learningPaths);
        return learningPaths;
    }
}
