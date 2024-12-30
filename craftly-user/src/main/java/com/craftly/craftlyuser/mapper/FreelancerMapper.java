package com.craftly.craftlyuser.mapper;

import com.craftly.craftlycore.user.FreelancerCreateDTO;
import com.craftly.craftlycore.user.UserCreateDTO;
import com.craftly.craftlyuser.entity.Freelancer;
import org.springframework.stereotype.Component;

@Component
public class FreelancerMapper {

    public Freelancer toEntity(FreelancerCreateDTO freelancerCreateDTO) {
        return Freelancer.builder()
                .specializations(freelancerCreateDTO.specializations())
                .portfolioIds(freelancerCreateDTO.portfolioIds())
                .build();
    }

    public FreelancerCreateDTO toFreelancerCreateDTO(UserCreateDTO userCreateDTO) {
        return new FreelancerCreateDTO(
                userCreateDTO.specializations(),
                userCreateDTO.portfolioIds()
        );
    }
}
