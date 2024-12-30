package com.craftly.craftlyuser.repository;

import com.craftly.craftlyuser.entity.Employer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployerRepository extends ReactiveCrudRepository<Employer, UUID> {
}
