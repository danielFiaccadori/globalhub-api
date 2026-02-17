package com.globalhub.main.application.dto;

import com.globalhub.main.domain.user.UserRole;

public record UserRegisterDTO(String email, String password, UserRole role) {
}
