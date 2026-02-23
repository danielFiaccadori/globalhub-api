package com.globalhub.main.application.ia;

import java.util.List;

public record GeminiResponseDTO(List<Candidate> candidates) {

    public String extractText() {
        return candidates().getFirst().content().parts().getFirst().text();
    }
}

record Candidate(Content content) {}
