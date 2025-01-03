package com.craftly.craftlyuser.service.freelancer;

import com.craftly.craftlycore.models.user.FreelancerCreateDTO;
import com.craftly.craftlyuser.entity.Freelancer;
import com.craftly.craftlyuser.mapper.FreelancerMapper;
import com.craftly.craftlyuser.repository.FreelancerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreelancerWriteServiceDB {

    private final FreelancerRepository freelancerRepository;

    private final FreelancerMapper freelancerMapper;

    public Mono<Freelancer> createFreelancer(FreelancerCreateDTO freelancerCreateDTO) {
        return freelancerRepository.save(freelancerMapper.toEntity(freelancerCreateDTO))
                .doOnSuccess(freelancer -> log.info("Freelancer created. Freelancer ID: {}", freelancer.getId()))
                .doOnError(e -> log.error("Failed to create freelancer: {}", e.getMessage()));
    }
}
