package com.globalhub.main.application.ia;

import java.util.List;

public record GeminiRequestDTO(List<Content> contents) {

    public static GeminiRequestDTO of(String text) {
        return new GeminiRequestDTO(List.of(new Content(List.of(new Part(text)))));
    }

}

record Content(List<Part> parts) {}
record Part(String text) {}
