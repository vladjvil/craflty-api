package com.craftly.craftlycore.models.user;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        String username,
        String phoneNumber,
        String passwordHash
)  { }