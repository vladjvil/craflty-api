package com.craftly.craftlyuser.service;

import com.craftly.craftlycore.user.EmployerCreateDTO;
import com.craftly.craftlyuser.entity.Employer;
import com.craftly.craftlyuser.mapper.EmployerMapper;
import com.craftly.craftlyuser.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerWriteServiceDB {

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;

    public Mono<Employer> createEmployer(EmployerCreateDTO employerCreateDTO) {
        return employerRepository.save(employerMapper.toEntity(employerCreateDTO))
                .doOnSuccess(e -> log.info("Employer created: {}", e))
                .doOnError(e -> log.error("Failed to create employer: {}", e.getMessage()));
    }
}

