package com.globalhub.main.application.dto;

import jakarta.annotation.Nullable;

public record AuthenticationResponseDTO(@Nullable String token, String message) {
}
