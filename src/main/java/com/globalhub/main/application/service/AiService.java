package com.globalhub.main.application.service;

import com.globalhub.main.application.ia.GeminiRequestDTO;
import com.globalhub.main.application.ia.GeminiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AiService {

    private final RestClient restClient;
    private final String apiUrl;
    private final String apiKey;

    public AiService(RestClient.Builder restClientBuilder,
                     @Value("${gemini.api.url}") String apiUrl,
                     @Value("${gemini.api.key}") String apiKey) {
        this.restClient = restClientBuilder.build();
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public String generateCourseDescription(String courseName, String details) {
        String prompt = String.format(
                "Você é um redator especialista em escolas de cursos(como inglês, música, informática, programação e etc...). " +
                        "Escreva uma descrição engajadora e comercial de no máximo 3 parágrafos para o seguinte curso: " +
                        "Nome do Curso: %s. " +
                        "Detalhes adicionais: %s. " +
                        "O tom deve ser inspirador e focado no aprendizado prático.",
                courseName, details
        );

        GeminiRequestDTO requestPayload = GeminiRequestDTO.of(prompt);

        GeminiResponseDTO response = restClient.post()
                .uri(apiUrl)
                .header("x-goog-api-key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestPayload)
                .retrieve()
                .body(GeminiResponseDTO.class);

        return response != null ? response.extractText() : "Não foi possível gerar a descrição.";
    }

}
