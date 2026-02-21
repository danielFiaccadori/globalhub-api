package com.globalhub.main.application.dto.user;

import java.util.UUID;

public record UserDetailsDTO(
   UUID id,
   String email,
   String role
) {}
