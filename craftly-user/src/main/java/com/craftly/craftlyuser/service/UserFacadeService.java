package com.craftly.craftlyuser.service;

import com.craftly.craftlycore.user.UserCreateDTO;
import com.craftly.craftlyuser.entity.User;
import com.craftly.craftlyuser.mapper.EmployerMapper;
import com.craftly.craftlyuser.mapper.FreelancerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacadeService {

    private final UserWriteServiceDB userWriteServiceDB;
    private final FreelancerWriteServiceDB freelancerWriteServiceDB;
    private final EmployerWriteServiceDB employerWriteServiceDB;
    private final FreelancerMapper freelancerMapper;
    private final EmployerMapper employerMapper;

    @Transactional
    public Mono<User> createUser(UserCreateDTO userCreateDTO) {
        return userWriteServiceDB.createBaseUser(userCreateDTO)
                .flatMap(user -> {
                    switch (user.getJobStatus()) {
                        case FREELANCER:
                            return freelancerWriteServiceDB.createFreelancer(freelancerMapper.toFreelancerCreateDTO(userCreateDTO))
                                    .thenReturn(user);
                        case EMPLOYER:
                            return employerWriteServiceDB.createEmployer(employerMapper.toEmployerCreateDTO(userCreateDTO))
                                    .thenReturn(user);
                        default:
                            return Mono.error(new IllegalArgumentException("Invalid job status"));
                    }
                });
    }
}
