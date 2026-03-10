package com.globalhub.main.application.dto.user;

import com.globalhub.main.domain.user.UserRole;

public record UserRegisterDTO(String email, String password, String name, String cpf, UserRole role) {
}
