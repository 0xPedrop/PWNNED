package com.pwnned.domain.service;

import com.pwnned.adapter.output.http.LabOrchestratorAdapter;
import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.output.jpa.repository.util.SnowflakeIdGenerator;
import com.pwnned.adapter.output.redis.LaboratoryRedisAdapter;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.exception.LaboratoryNotFoundException;
import com.pwnned.domain.exception.LearningPathNotFoundException;
import com.pwnned.adapter.output.http.LabOrchestratorAdapter;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.port.input.LaboratoryServicePort;
import com.pwnned.port.output.LaboratoryRepositoryPort;
import com.pwnned.port.output.LearningPathRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class LaboratoryService implements LaboratoryServicePort {

    private final LabOrchestratorAdapter labOrchestratorAdapter;
    private final LaboratoryRepositoryPort laboratoryRepositoryPort;
    private final LaboratoryRedisAdapter laboratoryRedisAdapter;
    private final LearningPathRepositoryPort learningPathRepositoryPort;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public LaboratoryService(LaboratoryRepositoryPort laboratoryRepositoryPort,
                             LaboratoryRedisAdapter laboratoryRedisAdapter,
                             LearningPathRepositoryPort learningPathRepositoryPort,
                             SnowflakeIdGenerator snowflakeIdGenerator,
                             LabOrchestratorAdapter labOrchestratorAdapter) { 
        this.laboratoryRepositoryPort = laboratoryRepositoryPort;
        this.laboratoryRedisAdapter = laboratoryRedisAdapter;
        this.learningPathRepositoryPort = learningPathRepositoryPort;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
        this.labOrchestratorAdapter = labOrchestratorAdapter; 
    }

    @Override
    @Transactional
    public Laboratory createLaboratory(LaboratoryDTO laboratoryDTO) {
        LearningPath learningPath = learningPathRepositoryPort.findById(laboratoryDTO.learningPathId())
                .orElseThrow(() -> new LearningPathNotFoundException("Learning Path ID "
                        + laboratoryDTO.learningPathId() + " not found"));

        Laboratory newLaboratory = new Laboratory();
        newLaboratory.setLabId(snowflakeIdGenerator.nextId());
        newLaboratory.setTitle(laboratoryDTO.title());
        newLaboratory.setDifficulty(laboratoryDTO.difficulty());
        newLaboratory.setLaboratoryType(laboratoryDTO.laboratoryType());
        newLaboratory.setLearningPath(learningPath);

        Laboratory savedLaboratory = laboratoryRepositoryPort.save(newLaboratory);

        laboratoryRedisAdapter.invalidateCacheForLaboratoriesByLearningPathId(laboratoryDTO.learningPathId());
        laboratoryRedisAdapter.invalidateCacheForLaboratoriesByType(laboratoryDTO.laboratoryType().name());

        return savedLaboratory;
    }

    @Override
    public Page<Laboratory> getAllLaboratories(Pageable pageable) {
        return laboratoryRepositoryPort.findAll(pageable);
    }

    @Override
    public Laboratory getSingleLaboratory(Long laboratoryId) {
        return laboratoryRedisAdapter.getCachedLaboratory(laboratoryId)
                .orElseGet(() -> {
                    Laboratory laboratory = laboratoryRepositoryPort.findById(laboratoryId)
                            .orElseThrow(() -> new LaboratoryNotFoundException("Laboratory not found with ID: " + laboratoryId));

                    laboratoryRedisAdapter.cacheLaboratory(laboratory);
                    return laboratory;
                });
    }

    @Override
    @Transactional
    public void deleteLaboratory(Long laboratoryId) {
        Laboratory laboratory = laboratoryRepositoryPort.findById(laboratoryId)
                .orElseThrow(() -> new LaboratoryNotFoundException("Laboratory " + laboratoryId + " Not Found"));

        Long pathId = laboratory.getLearningPath().getLearningPathId();
        String type = laboratory.getLaboratoryType().name();

        laboratoryRepositoryPort.deleteById(laboratoryId);

        laboratoryRedisAdapter.invalidateCacheForLaboratory(laboratoryId);
        laboratoryRedisAdapter.invalidateCacheForLaboratoriesByLearningPathId(pathId);
        laboratoryRedisAdapter.invalidateCacheForLaboratoriesByType(type);
    }

    @Override
    @Transactional
    public void deleteAllLaboratories() {
        laboratoryRepositoryPort.deleteAll();
        laboratoryRedisAdapter.invalidateAllLaboratoriesCache();
    }

    @Override
    public List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType) {
        return laboratoryRedisAdapter.getCachedLaboratoriesByType(laboratoryType.name())
                .orElseGet(() -> {
                    List<Laboratory> laboratories = laboratoryRepositoryPort.getLaboratoriesByType(laboratoryType);
                    if (!laboratories.isEmpty()) {
                        laboratoryRedisAdapter.cacheLaboratoryByType(laboratoryType.name(), laboratories);
                    }
                    return laboratories;
                });
    }

    @Override
    public List<Laboratory> getLaboratoriesByLearningPathId(Long learningPathId) {
        return laboratoryRedisAdapter.getCachedLaboratoriesByLearningPathId(learningPathId)
                .orElseGet(() -> {
                    learningPathRepositoryPort.findById(learningPathId)
                            .orElseThrow(() -> new LearningPathNotFoundException("Learning Path with ID "
                                    + learningPathId + " not found."));

                    List<Laboratory> laboratories = laboratoryRepositoryPort.findByLearningPathId(learningPathId);
                    if (!laboratories.isEmpty()) {
                        laboratoryRedisAdapter.cacheLaboratoriesByLearningPathId(learningPathId, laboratories);
                    }
                    return laboratories;
                });
    }
    @Override
    public String startLaboratory(Long laboratoryId, String userId) {
        Laboratory laboratory = getSingleLaboratory(laboratoryId);


        String dockerImage = resolveDockerImage(laboratoryId); 

        return labOrchestratorAdapter.startLab(
            userId, 
            laboratory.getLaboratoryType().name(), 
            laboratoryId,
            dockerImage 
        );
    }
    private String resolveDockerImage(Long labId) {

        if (labId == 101L) return "pedropaulodel/lab-xss-reflected:latest";
        return "pwnned/default-lab:latest"; // Fallback
    }
}