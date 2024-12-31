package com.craftly.craftlyuser.mapper;

import com.craftly.craftlycore.models.user.EmployerCreateDTO;
import com.craftly.craftlycore.models.user.UserCreateDTO;
import com.craftly.craftlyuser.entity.Employer;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {

    public Employer toEntity(EmployerCreateDTO employerCreateDTO) {
        return Employer.builder()
                .companyName(employerCreateDTO.companyName())
                .companyWebsite(employerCreateDTO.companyWebsite())
                .build();
    }

    public EmployerCreateDTO toEmployerCreateDTO(UserCreateDTO userCreateDTO) {
        return new EmployerCreateDTO(
                userCreateDTO.companyName(),
                userCreateDTO.companyWebsite()
        );
    }
}
