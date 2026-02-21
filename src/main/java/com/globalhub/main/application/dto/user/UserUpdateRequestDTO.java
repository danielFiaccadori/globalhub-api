package com.globalhub.main.application.dto.user;

import com.globalhub.main.domain.user.UserRole;
import jakarta.annotation.Nullable;

public record UserUpdateRequestDTO(@Nullable String newEmail, @Nullable UserRole newRole) {
}
