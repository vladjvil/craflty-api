package com.craftly.craftlycore.user;

import java.util.List;
import java.util.UUID;

public record FreelancerCreateDTO(
        List<FreelancerSpecialization> specializations,
        List<UUID> portfolioIds
) { }
