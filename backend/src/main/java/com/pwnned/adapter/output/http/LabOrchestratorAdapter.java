package com.pwnned.adapter.output.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.HashMap;

@Component
public class LabOrchestratorAdapter {

    // URL do serviço no Docker Compose ou Kubernetes
    @Value("${pwnned.orchestrator.url:http://labs-orchestrator:3000}")
    private String orchestratorUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String startLab(String userId, String labType, Long labId, String dockerImage) {
    String url = orchestratorUrl + "/spawn";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> body = new HashMap<>();
    body.put("userId", userId);
    body.put("labType", labType);
    body.put("labId", labId);
    body.put("image", dockerImage); 

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

    try {
        Map response = restTemplate.postForObject(url, request, Map.class);
        return (String) response.get("url");
    } catch (Exception e) {
        throw new RuntimeException("Erro ao comunicar com o Orchestrator: " + e.getMessage());
    }
}
}