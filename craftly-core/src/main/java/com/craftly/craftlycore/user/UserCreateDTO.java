package com.craftly.craftlycore.user;

import java.util.List;
import java.util.UUID;

public record UserCreateDTO(
        String email,
        String username,
        String phoneNumber,
        String password,
        UserJobStatus jobStatus,
        List<FreelancerSpecialization> specializations,
        List<UUID> portfolioIds,
        String companyName,
        String companyWebsite
) {}